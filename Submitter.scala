/*
 * Developer : Pierre-Yves Aquilanti (py [dot] aquilanti [at] gmail.com)
 * Date : 28/8/12
 * All code (c)2012 PY Aquilanti. all rights reserved
 */

import java.io.File
import scala.io.Source
import scala.collection._
import scala.util.matching._

object Submitter {

  val usage = """
    Usage: Submitter.scala config_filename
  """

	def main(args: Array[String]) {
		Console.err.println(Console.GREEN+"Launching submitter system."+Console.RESET)	
		//check if a param file has been provided by the user
		if(args.length!=1){
			Console.err.println(Console.RED+"No input file provided."+Console.MAGENTA+usage+Console.RESET)
			System.exit(1)
		} else if(!fileExist(args(0))){ // or if file exist 
			Console.err.println(Console.RED+"Input file "+args(0)+" provided does not exist."+Console.RESET)
			System.exit(1)
		}

		//get file config map
		Console.err.println(Console.GREEN+"Getting config file : "+Console.RESET+args(0))	
		val configMap = ConfigProvider.fileToMap(args(0))

		//is the file valid
		if(!ConfigProvider.isConfigMapValid(configMap,"COMMAND")){
			Console.err.println(Console.RED+"Input file "+args(0)+" does not contain a COMMAND \nconfiguration line or this line is invalid."+Console.RESET)
			System.exit(1)
		} else Console.err.println(Console.GREEN+"Launching provided config file is valid."+Console.RESET)	
		
		Console.err.println(Console.GREEN+"Extracting informations from config file."+Console.RESET)
		//Get the commands list with list of args
		val commandList = ConfigProvider.configToList("COMMAND",configMap)
    // compute the resulting command
    Console.err.println(Console.GREEN+"Constructing command list."+Console.RESET)
		val result = ConfigProvider.listToCommandList(configMap("COMMAND"), commandList)
    Console.err.println(Console.GREEN+"Outputing command list to standart output."+Console.RESET)
    result.foreach(println _)
    }


    def fileExist(file: String):Boolean = (new File(file)).isFile


    object ConfigProvider {
    	//check if a parameter map is valid
    	def isConfigMapValid(map: Map[String,String], command: String):Boolean = {
    		if(map.contains(command)){
    			val commandMap=extractCommandToMap(map(command))
    			//if the set is empty if yes then we consider that the parameter map
    			//is valid because all commands contained in the command string
    			//are contained in the parameter map, nothing is missing
    			(commandMap.keySet &~ map.keySet).isEmpty
    		} else false
    	}

    	//check if config file is valid
    	def isConfigFileValid(filePath: String, command: String):Boolean = {
    		val map = fileToMap(filePath)
    		isConfigMapValid(map,command)
    	}

    	//transform a file to a map
		def fileToMap(filePath: String):Map[String,String] = {
			val regex = """^(_?[a-zA-Z0-9]\w*_?)\s*=\s*\"(.*)\"#?.*$""".r
			val comArgs = mutable.Map[String, String]()

			for(line <-Source.fromFile(filePath).getLines)
			    for(regex(c,a) <- regex findAllIn line)
      				comArgs(c)=a
  			comArgs.toMap
		}
		
		//transform a string containing strings such as ${priority:command} to a map
		def extractCommandToMap(command: String):Map[String, Int] = {
  			val regex = """\$\{([0-9]+)?:?([\w]+)\}""".r
  			val com = mutable.Map[String, Int]()

  			//get the commands, remove all blank characters 
  			for (regex(c,b) <- regex findAllIn command.replaceAll("""\s""", ""))
  				com(b)= if(c!=null && c.toInt>0 && c.toInt<=99) c.toInt else 99
  			com.toMap
  	}

  	def replaceCommand(command:String, key:String, value:String):String = {
  		val regex = new Regex("\\$\\{([0-9]+)?:?"+key+"\\}") 
  		regex replaceAllIn ( command, value)
  	}


  	def configToList(command: String,configMap:Map[String,String]):List[(String,List[String])] = {
		//get the COMMAND config that contain the final command in value sorted order
  		val commandList = extractCommandToMap(configMap(command)).toList.sortBy(_._2).reverse
  		val configMapList:Map[String,List[String]] = for{(confk,confv) <- configMap.filterKeys(configMap.keySet & commandList.toMap.keySet)}
													yield (confk -> confv.split("[,:;]").toList)

		  for( com <- commandList) yield (com._1 -> configMapList(com._1))
  	}

  	def listToCommandList(command:String, list: List[(String,List[String])]):List[String] = {
      val batch:List[String] = if(!list.isEmpty) replaceToList(command,list.head._1,list.head._2) else List[String](command)

      if(!list.tail.isEmpty) {
        val ll = for(com <- batch) yield listToCommandList(com,list.tail)
        ll.foldLeft(List[String]())((a,b) => a ::: b)
      }else batch        
  	}

    def replaceToList(command: String, keyword: String, values:List[String]):List[String] = 
        for{value <- values} yield replaceCommand(command,keyword,value) 
	}
}

