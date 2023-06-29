# DiplomaMaker
DiplomaMaker is a free and open source application inspired by Battle City. It will help you generate a large number of diplomas from the provided template file and table with participants.
The application also has an editor that will allow you to create your own template for which diplomas will be generated.

## Participant table format:
The application can work with 3 types of spreadsheets: files in .csv format, files in .xlsx format and links to google spreadsheets.
The file format should be the following:
* Participant number (assumed to be a number)
* Last name of the participant
* Participant name
* Middle name of the participant
* Member School
* Subject of the Olympiad
* Diploma Degree

## Template:
* If you want to use a ready-made template, make sure that it contains the words "Фамилия Имя". The application will replace them with the names and surnames of the participants and generate a PDF file of diploma for each participant.
* If you want to create a custom template - just upload the pdf file and click the edit button. A window will open where you can enter the text that you want to see on diplomas. Save the result after editing

## Application setup
For the application to work correctly, the following libraries must be installed:
* PDFBox
* Apache POI
* Jackson
* google-api-client
* itextpdf
Also make sure that you have created a service key for working with Google api (json file). It should be called compact.json and be in the parsers folder
