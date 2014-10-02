imageRenamer
============

Backing up my images every month, Dropbox, Nexus 5 and Samsung uses different 
image-naming-standards. This java programs solves the problem.


What it does
--------
You have a folder with a bunch of all your images since you were 4.½ year old.
<br>
<br>
<b>Images</b>

-  <b>2013.05.12-2014.02.13</b>
    -  imageA.png
    -  2013.12.23-12.23.png
-  <b>school images</b>
    -  imageA.png
	  -  imageB.png
-  2013.12.23-12.23.png
-  2013.12.23-12.23(2).png

The output of this will be:
<br>
<br>
<b>Output</b>

-  <b>2012</b>
    -  <b>02</b>
        -  <b>school images</b>
            -  2012.02.12-12.03.png   <renamed from imageA.png>
            -  2012.02.12-12.05.png   <renamed from imageB.png>
        -  <b>2013.05.12-2014.02.13</b>
            -  2012.02.12-12.03.png   <renamed from imageA.png>

-  <b>2013</b>
    -  <b>12</b>
        -  <b>2013.05.12-2014.02.13</b>
            -  2013.12.23-12.23.png
            
As you see, the images will be sorted in folders depending on when the images were
taken. If the images were placed in a special folder they will be inherited 
to the new structure.

The Output-folder will be updated if you want to complement with new images. 

How to use it
--------

The gui is under development, but will be here soon. You may select what "special folders" 
that will be inherited to the new structure. 

The gui will make you select folder for input and point out an output folder. 

Generate all the files by using generate command
  > java -jar ImageRename -generate

Then populate the folders with Samsung and Dropbox images. 
  > java -jar ImageRename -run

Run the program and you end up with a merged result in the Output-folder.
 
Road map
--------

- [x] use image meta-data interpreters. 
- [ ] use some kind of mp4-meta data parser
- [ ] take an input folder and generate new folders from meta data
- [ ] create a simple gui selecting two folders(input/output)
- [ ] present the results of duplicates etc. 
- [ ] take care of output folder on update
- [ ] handle flavoured folders. 
