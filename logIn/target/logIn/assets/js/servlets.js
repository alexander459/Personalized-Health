//takes as parameter the doctors id and returns all the free randevouz using a servlet
function getFreeRandevouz(id){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                randevouz=JSON.parse(xhr.responseText);
            }else if(xhr.status===411){
                //no free randevoudia
                randevouz=null;
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetfreeRandevouz?id=' + id, false);
    xhr.send();
}

//returns all the randevouz booked by the user from a servlet
function getBookedRandevouz(userId){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                randevouz=JSON.parse(xhr.responseText);
            }else if(xhr.status===411) {      //no booked randevouz
                randevouz=null;
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetUsersRandevouz?user_id=' + userId, false);
    xhr.send();
}


//returns a treatment for the bloodtest with <id> using a servlet
function getTreatment(id){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                trtObj=JSON.parse(xhr.responseText);
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetTreatment?id=' + id, false);
    xhr.send();
}

//returns user's active treatments using a servlet
function getUserTreatment(user_id){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                trtObj=JSON.parse(xhr.responseText);
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetUserTreatment?user_id=' + user_id, false);
    xhr.send();
}



//calls a servlet and books the randevou
function bookRandevouz(){
    var rndvId=event.srcElement.id.split('_')[1];       //the randevou id
    var usrId, usrTel, usrAmka;
    var params;

    getCurrentUser();
    usrId=loggedInUser.user_id;
    usrTel=loggedInUser.telephone;
    usrAmka=loggedInUser.amka;

    params="randevouz_id=" + rndvId + "&user_id=" + usrId + "&user_tel=" + usrTel + "&user_amka=" + usrAmka;
    loggedInUser=null;

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("booked");
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'BookRandevou?' + params, false);
    xhr.send();
}

function cancelRandevouz(){
    var rndvId=event.srcElement.id.split('_')[1];       //the randevou id
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("canceled");
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'CancelRandevouz?randevouz_id=' + rndvId, false);
    xhr.send();
}


/*calls servlet GetBloodTests and returns all the bloodtests with the users amka*/
function getExams(amka){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                console.log(xhr.responseText)
                exams=JSON.parse(xhr.responseText)
            }else{
                alert(xhr.status)
            }
        }
    };

    xhr.open('GET', 'GetBloodtests?amka=' + amka, false);
    xhr.send();
}


/***************
 * SERVLETS
 ************** */



//post exams at the getExams servlet and stores them in the database
function postExams(){
    console.log('posting exams');
    var params="";
    params=formToParams(document.getElementById('exams-form'));

    //get the user to get the amka
    getCurrentUser();

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("exams added!");
                document.getElementById('exams-form').reset();
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    params+="&amka=" + loggedInUser.amka;                    //we also need amka to register bloodTest
    xhr.open('POST', 'AddExams', false);
    xhr.send(params);
    loggedInUser=null;  //destroy the user
}