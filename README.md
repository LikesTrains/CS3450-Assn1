Implementing this application:
1. Fork this repository into your computer and run the file "Register.", to do this you must have a Java Runtime Environment.
2. From this window you can select which side of the business you'd like to log into. The users database is located within the "src/data/users.txt" file, these follow the structure "User ID", "First Name", "Last Name", "Level" and "Password". Users of level 1 are assumed to be cashiers, which means they don't have access to the back office functionality. Users of levels above that are allowed to use this functionality to edit various values.
3. When logged into the "Checkout" side, input the ID of the item being sold as well as the amount being sold. Press Enter and it will be added to your order. To make a refund, just input the amount that you're looking to return with a negative sign in front of it. Once you're done inputting values, click the "ring up" button and select the option "yes" when prompted with the question of wether or not you'd like to ring up.
4. When logged into the "Back office" side, select which database you'd like to edit. After choosing one, edit an element within it by inputting the element's ID, press the search button and you'll be sent to a window where you'll be allowed to edit the data in this element.
