Image Sort Master
============
[![Build Status](https://travis-ci.org/kotlinski/image-sort-master.svg?branch=feature%2Ftravis)](https://travis-ci.org/Springworks/android-utils) [![Coverage Status](https://img.shields.io/coveralls/kotlinski/image-sort-master.svg)](https://coveralls.io/r/kotlinski/image-sort-master)

How and when do you sort and back up your images from your camera-devices? 

I have my images stored on a few harddrive backups, dropbox and various devices; 
My dropbox, my nexus 5 and my old Samsung galaxy device. The problem is that 
Dropbox, Nexus 5 and Samsung all have their own way of naming the images and their 
storages always get full at different points of time. Dropbox gets full once in a year, 
my phone every two or three months, or sometimes, devices get wasted. 

Over time it gets hard to detect duplicates and you don't bother delete poor-
quality images, because you know that it may exist in at least one more location.

With this tool your images will be sorted and renamed in this structure:
<br>
`year/[month|quarter|nothing]/<flavor>/yyyy-mm-dd hh:mm:ss.[png|jpg|mp4]`
<br>
The month setting is optional, your different options is month, quarter or none. 
The flavor may be Instagram, Screenshots, Elins wedding or what folder you 
put your images in.<br>
A flavor is all subfolders from the root folder, except year/quarter/month.
<br>Example:<br>
`Root/2013/Wedding/TheKiss/imgA.png`
 <br>will be sorted as:<br> 
`2013/04/Wedding/TheKiss/2013-04-22 13:17:00.png`
<br>


Donate
--------
Bitcoin address `1CpVkqhrGbcQmD5WPuKsyWxPrJTiaVdmDq`

![Bitcoin address](chart.png)


Pseudo code
--------
 - Select folders to be merged
 - Select master-folder where the images will end up
 - Parse all images and videos from selected folders.
     - Detect flavors 
     - Read the image/video meta-data 
     - Calcucate an unique id(MD5) of each image.
     - Make a set of MD5.
 - Execute, export to master folder
 - Create report of duplicates and put it in output root folder
     - May contain a list of duplicate images.
     - Lists of renamed files etc. 
 

Example of an execution
--------
This is my problem: 
I have a great back-up folder for images on different harddrives. With sub-folder
names like '2011-12 to 2012-05', '2012 summer','dropbox 2012' etc. 
And then in the middle of everything, your out of phone memory and need to dump 
all images from your phone. 
<br>
<br>
You have a folder with a bunch of all your images since you were 4.½ year old 
and this tool helps you cleaning everything up and putting it in nice folders.
<br>
<br>
This may be some image you want to merge to one all mighty: 
<br>
<br>
<b>Images from phone</b>

-  <b>Camera</b>
    -  camImgA.png
    -  camImgB.png
-  <b>Instagram</b>
    -  instaImgA.png
	  -  instaImgB.png
-  <b>Screenshots</b>
    -  scrnShtA.png
	  -  scrnShtB.png

<br><br>
<b>Images from Dropbox</b>

-  <b>Camera Uploads</b>
    -  <b>Elins Wedding</b>
      -  image_A.png
      -  image_C.png
    -  image_D.png
    
 
And then you may want to merge those image folders to a master backup location, that 
may already exist :
<br>
<br>
<b>My Image Back up</b>

-  <b>2012</b>
    -  <b>02</b>
        -  <b>Camera</b>
        -  <b>Instagram</b>
        -  <b>Screenshots</b>

-  <b>2013</b>
    -  <b>12</b>
        -  <b>2013.05.12-2014.02.13</b>
            -  2013.12.23-12.23.png
            
As you see, the images will be sorted in folders depending on when the images were
taken. If the images were placed in a special folder they will be inherited 
to the new structure.

The Output-folder will be updated if you want to complement with new images.
 
Step 1. 
 - Select all your folders you want to merge. <b>Images from phone</b>, 
<b>Images from Dropbox</b> and <b>My Image Back up</b> 
 
Step 2. 
 - Mark your master folder, either a new folder or an existing one. In this 
  case <b>My Image Back up</b>. 
 
Step 3. 
 - Select if output folders will be sorted by moths, quarters directly under year.
  
Step 4.
 - Execute


How to use it
--------

The gui is under development, but will be here soon. You may select what "special folders" 
that will be inherited to the new structure. 

Make a jar with gradle: <br> 
`  > gradlew build` <br>
`  > java -jar imageRenamer.jar` <br>
.. or generate a bat with:  <br>
`  > gradlew installApp`

A nice way to set up the run configurations in Intellij is:
Run > Edit Configurations > Add a new Application Configuration.

Run the program and you end up with a merged result in the Master-folder.
 
Road map
--------

- [x] use image meta-data interpreters. 
- [x] use some kind of mp4-meta data parser
- [x] take an input folder and generate new folders from meta data
- [ ] create a simple gui selecting two folders(input/output)
- [ ] present the results of duplicates etc. 
- [x] take care of output folder on update
- [ ] print out new file structure as default, add parameter to execute
- [ ] add parameter list of flavours not should be accepted. 

