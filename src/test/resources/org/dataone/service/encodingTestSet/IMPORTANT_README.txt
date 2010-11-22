The file ./testUnicodeStrings.utf8.txt contains various character strings to test
correct pct-escaping of content in URLs.  The primary use case is being able to
handle guids / identifiers that use special characters or characters outside the
ASCII range.  

!! BEFORE YOU MAKE CHANGES TO THE FILE: 
!!   BE CERTAIN YOU ARE USING AN EDITOR THAT CAN SAVE AS UTF-8 !!
!!  -- one free editor that can do this is TextWrangler (MacOSX)

* The file is saved using UTF-8 encoding, 
* Newlines separated using Unix LF convention.
* The file uses tab to separate two columns
* The first column is the unencoded / unescaped content
* The second column contains the encoded/escaped character string

Encoding for a string varies according to where it will be placed in the 
URL. (Path, Query, Fragment).  Accordingly, a keyword was added as a prefix
to the content to aid in using these in test cases:
	"common-" 
	"path-"	
	"query-"
	"fragment-"
	
To test a function that encodes for path segments, choose only those that begin 
with "path-" or "common-". Note, it is important to include the common- ones, as
all of the non-ascii examples are in that set.


 