
function showDoctor(){
    var btnContainer;   //button container for the users buttons
    var addExmsBtn;     //button to add exams
    var seeExmsBtn;     //button to see the exams
    var findDocsBtn;    //button for finding doctors


    getCurrentUser();       //update the current user
    main.innerHTML="";
    //add the users buttons
    showGreetingMsg(loggedInUser.username);
    loggedInUser=null;  //destroy
}