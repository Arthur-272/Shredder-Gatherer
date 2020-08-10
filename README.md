# Shredder-Gatherer
This is a tool that shreds a file and store it locally on your computer in encrypted chunks, and when you want to use the file then use the same tool to gather the file.

**Shredder.java:-**
Its purpose is to shred the file whose path is given when asked in number or chunks of fixed number of bytes, and then store it locally on the computer on the local machine. It also encrypts every chunk with AES-256 encryption algorithm. So now your file is not in one place but all over your computer.

**Gatherer.java:-**
This is the file that will help you get back the file that you shredded with the help of shredder.java. You have to enter the name of the file that you shredded (with extension) and then it will gather all the chunks of data and decrypt it and then put it together and recreate your whole file.
