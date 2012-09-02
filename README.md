CComposer
=========

Scala script that output list of strings based on an input config file. It is particularly useful when you need to create a list of commands based on some parameters. 

In my case, I sometimes need to run a lot of tests or benchmarks using different parameters (numbers of used processors for example) which are different for each command. Instead of doing this by hand, I prefer to do this automatically.

How to use 
----------

You need a input file like below, it must have a COMMAND field, which is the one on which is based our command construction. Furthermore, each field to replace within command will have to be present. For example, on the picture below we have *nproc* and *restart*. 

###### Input file

![Input example](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_input.png)

###### Resulting output

![Resulting output example](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_output.png)



### A few things are to be respected :
- an input file must be supplied (see *No input file supplied*)
- fields values must be between double quotes (see *Input example*).
- each field to replace within command will have to be present. Otherwise the result displayed in *Input file* example will produce the *Wrong input file* example.
- fields present in command must be prepended by a dollar sign and between curly braces such as : **${field}**, otherwise it will produce the *Wrong input file* example.
- it is possible to precise a priority of iteration in the command by putting a number before the field in curly braces, this priority number must be between *0* (high priority) and *99* (low priority). You can compare the first *Input* and *Output* example with the one called *Priority*
- You can redirect the output to a file like in example *Redirection*. Just FYI, information is displayed using stderr and the result output is displayed through stdout.

#### Wrong input file

###### Input file 

![Wrong input file ](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_wrong_input.png)

###### Resulting output 

![Wrong output file ](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_wrong_input_outpout.png)



#### No input file supplied

###### Resulting output 

![No output file ](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_no_input.png)


#### Priority

###### Input file 

![Priority input](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_input_priority.png)

###### Resulting output 

![Priority output](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_output_priority.png)


#### Redirection

###### Input file 

![Redirection input](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_output_redirect.png)

###### Resulting output 

![Redirection output](https://raw.github.com/Perif/CComposer/master/documentation%20ressources/sample_output_redirect_output.png)




####Why using Scala ?
So why did I use Scala instead of Python or Ruby which would have corresponded more to this type of program/script ?

Well, the answer is simple, I'm actually learning Scala and now writing my scripts in this language to understand how it works. I totally confess that is not the most efficient way to write this kind of program, that my code is not super well written, nor optimised, but who cares ? It works, it can be reused, and if I want to take the time for that, It will be rewrote to a better code.


#### Further notes :

This software is released using GPL. Please feel free to contact me for any suggestion or improvements. Thanks !

#### GPL & Copyright :

Copyright 2012 Pierre-Yves Aquilanti

This file is part of CComposer.

CComposer is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

CComposer is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with CComposer. If not, see http://www.gnu.org/licenses/.