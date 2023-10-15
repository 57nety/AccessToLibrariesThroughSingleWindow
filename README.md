# Access to libraries through a single window.
This app was my thesis project during my undergraduate studies. <br>
It application allows you to log in once and simultaneously interact with several third-party services.
+ API integration of third-party electronic library systems has been implemented.
+ Authorization in the application occurs through user authentication at a remote distance.
+ The application does not store user login and password.
+ The application can only be used by a person who has a password and login issued by AltSTU.
+ The application integrates two electronic library systems: 
  + the electronic library system of Altai State Technical University
  + the IPR SMART digital educational resource.
+ The ability to simultaneously search through two libraries, both books and disciplines, has been implemented.
+ AltSTU EBS API provides the ability to download a book; this functionality is implemented in the application.
+ The user's personal account has been implemented, which displays:
  + history of downloaded books
  + search history, indicating the subject of the search (book or discipline)
  + books marked by the user as favorites
+ To store information contained in the user’s personal account, were used JPA and PostgreSQL.
+ All UI was implemented using a  Html, CSS, Js and template engine Thymeleaf.
+ All business logic of the application is implemented in Java using Spring.

Since the functionality of interaction with APIs of third-party electronic library systems is confidential, <br>
therefore the project does not have a properties.yaml file, which contains key information for implementing that. <br>
**Without this file the application will not work.**

Attached to this project is a presentation **GraduateWork.pdf** that reflects the logic of the application <br>
and provides screenshots of its operation.