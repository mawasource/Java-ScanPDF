# Java PDF Sorter

## Requirement
PDF document with multiple sites with the same layout.

## What is it?

*Java PDF Sorter* is a Java Desktop application which can read a specified field (integer) of each site of a PDF file. The pdf file must contains multiple sites with the same layout. The *scanner* reads on each site the specified field, parses it to an integer, sorts the integers and outputs the new sorted pdf.

see PDFProcessor.java -> private final Rectangle rectNumber = new Rectangle(160, 240, 90, 30); 