**My Clinic**   <br />
The project is supposed to help patients book appointments with doctors 👨🏾‍  <br />
⚕️. Patients can log in to the app, where they shall find the default list of doctors.  <br />
They could choose city names and doctors from the list. (City and doctors lists are provided by API 🖥️.  <br />
Also, doctors' data is paginated). The doctors’ data shall be shown in infinite scrolling manner.  <br />

**Screens** 🪟   <br />

- Login Screen (VALIDATE) 🙅🏾‍♂  ️ <br /> 
- Doctor List Screen (VIEW) 🧵   <br />
- City List (CHOOSE) 🏙️   <br />
- Send Appointment Request (ACT) 🙋🏽‍♂   <br />️

**App Flow** 🌊    <br />

[VALIDATE]-> [VIEW] -> Click on *"Book Appointment*" -> Open [ACT] -> Send Booking Request  <br />

*Change City -> [CHOOSE]-> [VIEW]refreshed with updated data   <br />

**Screens Details** 🧑🏾‍🏫   <br />

1. [VALIDATE]   <br />
   Enter Phone Number 🔢(should be a valid Indian 🇮🇳 phone number and the only phone number allowed is 8448569131)   <br />
   "Login" button 🔘   <br />
2. [VIEW]  
   List of Doctors (Display all info of doctor which you get from API) 📺   <br />
   "Book Appointment" button 🔘 against each doctor in the list   <br />
   Option to change/select "City"   <br />
3. [CHOOSE]   <br />
   List of cities from endpoint   <br />
4. [ACT]   <br />
   Form 📃to enter all the patient details required in endpoint (with checks e.g. *we expect that you would not let patient input age as -1 (negative one)*)   <br />
   "Book" Button 🔘   <br />