1)The first thing you have to do is to open your xampp control panel
then turn on APACHE and MYSQL then navigate to Mysql Admin and create a new database called 
"hotel_management" and import the sql file that you'll find in the repository.

2)next step is to download the javafx sdk "javafx-sdk-17.0.12"
and also the jfoenix "9.0.10" jar 

3)What you have to do next is to include them in the project structure 
File/Project Structure/Libraries and add the lib files to the libraries of the projects 
you have also to add in the modules the "javafx-sdk-17.0.12" 
you will find in the fourth step how your project structure should be so the project run correctly 

4)make sure that your project structure contains this 
![image](https://github.com/user-attachments/assets/920dff3e-2349-4947-8806-9721f97b5e2f)
![image](https://github.com/user-attachments/assets/310c64af-d11b-4fd7-98fc-0e0b881d2492)
![image](https://github.com/user-attachments/assets/8122ad5f-5d97-4a4a-b925-34c6ae0f3142)
![image](https://github.com/user-attachments/assets/f90e773a-612e-4b12-9c7d-4393cbf72849)

5)then you have to make a new running configuration 
![image](https://github.com/user-attachments/assets/1de88ca7-0920-4950-b0b9-6f62e3aa38b1)
with adding vm options    --module-path "C:\Users\lenovo\.jdks\javafx-sdk-17.0.12\lib" --add-modules javafx.controls,javafx.fxml
you can simply add new running configuration here 
![image](https://github.com/user-attachments/assets/a3fc6c11-67e4-4cca-ae9d-f4a4d9cbbe05)
through clicking on edit configurations and then add vm options 
![image](https://github.com/user-attachments/assets/c6e6b9ce-f360-4563-9354-2381d3a8e71d)






Ps:Making this steps ususally will be enough for the project ro run correctly and with no issues 
if you came across any problem please don't hesitate to ask me for help , Thank you .
