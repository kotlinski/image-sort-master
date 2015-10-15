Image Sort Master
============

[![Build Status](https://travis-ci.org/kotlinski/image-sort-master.svg?branch=feature%2Ftravis)](https://travis-ci.org/kotlinski/image-sort-master) [![Coverage Status](https://img.shields.io/coveralls/kotlinski/image-sort-master.svg)](https://coveralls.io/r/kotlinski/image-sort-master)

Purpose
--------

How and when do you sort and back up your images from your camera-devices? 

I have my images stored on a few hard drive backups, dropbox and various devices;
My dropbox, my nexus 5 and my old Samsung galaxy device. The problem is that 
Dropbox, Nexus 5 and Samsung all have their own way of naming the images and their 
storage's always get full at different points of time. Dropbox gets full once in a year,
my phone every two or three months, or sometimes, devices get wasted. 

Over time it gets hard to detect duplicates and you don't bother delete poor-
quality images, because you know that it may exist in at least one more location.

With this tool your images will be sorted and renamed in this structure:
<br>
`year/[month(optional)]/<flavour(existing folder structure)>/yyyy-mm-dd hh:mm:ss.[png|jpg|mp4]`
<br>
The month setting is optional. 
The flavor may be Instagram, Screenshots, Elins wedding or what folder you
put your images in.<br>
A flavor is all sub folders from the root folder, except year/month.
<br>Example:<br>
`root-folder/2013/Wedding/TheKiss/imgA.png`
 <br>will be sorted as:<br> 
`root-folder/2013/04/Wedding/TheKiss/2013-04-22 13:17:00.png`
<br>
The application will detect all duplicate files and merge them into one when possible. 


Donate
--------
Bitcoin address `36h3FR2xqVZg14TASLMPvDfETBDziUb1oo`

![Bitcoin address](chart.png)


Pseudo code
--------
 - Select folder where your images are
 - Parse all images and videos from selected folder.
     - Detect flavors 
     - Read the image/video meta-data 
     - Calculate an unique id(MD5) of each image.
     - Make a set of MD5.
 - Execute, export to master folder
 - Create report of duplicates and put it in output root folder
     - May contain a list of duplicate images.
     - Lists of renamed files etc. 
 

A real life situation
--------
<br>
<b>Images from phone</b>

-  <b>Camera</b>
    -  cam_ImgA.png
    -  cam_ImgB.png
-  <b>Instagram</b>
    -  insta_ImgA.png
	  -  insta_ImgB.png
-  <b>Screenshots</b>
    -  scrn_ShtA.png
	  -  scrn_ShtB.png

<br><br>
<b>Images from Dropbox</b>

-  <b>Camera Uploads</b>
    -  <b>Elins Wedding</b>
      -  image_A.png
      -  image_C.png
    -  image_D.png
    
 
The application will help you merge the result into this:
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
        -  2013.12.23-12.23.png
            
As you see, the images will be sorted in folders depending on when the images were
taken. If the images were placed in a special folder they will be inherited 
to the new structure.

The Output-folder will be updated if you want to complement with new images.


How to build and use it
--------

### Setup the project
Make a runnable bat with:  <br>
`  > ./gradlew installApp` <br>
`  > build/install/image-clients/bat/image-clients.bat -h` <br>


To run unit tests and integration test run: <br> 
`  > ./gradlew clean build` <br>
To only run unit or integration tests: <br>
`  > ./gradlew clean build -x integrationTest` <br>
`  > ./gradlew clean build -x test` 

A guide for splitting up unit and integration tests in gradle: 
[Integration Testing With the TestSets Plugin](http://www.petrikainulainen.net/programming/gradle/getting-started-with-gradle-integration-testing-with-the-testsets-plugin/)

### GUI 
Not in MVP 1

### Command-line interface

```
 -h,--help           print help
 -s,--source <arg>   Folder to 'image sort'
```

Road map
--------

- [x] use image meta-data interpreters. 
- [x] use some kind of mp4-meta data parser
- [x] take an input folder and generate new folders from meta data
- [x] use dependency injection, (Guice etc)
- [x] add automatic builds
- [ ] create a simple gui selecting two folders(input/output)
- [ ] present the results of duplicates etc.
- [x] take care of output folder on update
- [ ] print out new file structure as default, add parameter to execute
- [ ] add parameter list of flavours not should be accepted. 

