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
    console.log("id " + id)
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
    var usrId, usrTel, usrAmka, usrEmail;
    var params;

    getCurrentUser();
    usrId=loggedInUser.user_id;
    usrTel=loggedInUser.telephone;
    usrAmka=loggedInUser.amka;
    usrEmail=loggedInUser.email;
    loggedInUser=null;

    params="randevouz_id=" + rndvId + "&user_id=" + usrId + "&user_tel=" + usrTel + "&user_amka=" + usrAmka + "&user_email=" + usrEmail;


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
    if(xhr.status===200)
        document.getElementById(event.srcElement.id).parentElement.parentElement.style.display="none";
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
    document.getElementById(event.srcElement.id).parentElement.parentElement.style.display='none';
}


/*calls servlet GetBloodTests and returns all the bloodtests with the users amka*/
function getExams(amka){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                exams=JSON.parse(xhr.responseText)
            }else{
                alert(xhr.status)
            }
        }
    };

    xhr.open('GET', 'GetBloodtests?amka=' + amka, false);
    xhr.send();
}


/*calls servlet GetBloodTests and returns all the bloodtests with the users amka*/
function getClientsExams(client){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                exams=JSON.parse(xhr.responseText);
                console.log(exams);
            }else{
                alert(xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetClientBloodtests?client=' + client, false);
    xhr.send();
}



//post exams at the getExams servlet and stores them in the database
function postExams(){
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

function sendMessageClientToDoc(){
    var doc_id=event.srcElement.id.split('_')[1];
    var user_id;
    var text=document.getElementById('message-area').value;
    var params;

    getCurrentUser();
    user_id=loggedInUser.user_id;
    loggedInUser=null;
    params='doctor_id=' + doc_id + '&user_id=' + user_id + '&message=' + text;


    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("msg sent!");
                document.getElementById('message-area').value="";
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'SendMessageToDoc?' + params, false);
    xhr.send();
}

function getMessagesFromClient(client){
    const xhr = new XMLHttpRequest();
    var msg=[];
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                msg=JSON.parse(xhr.responseText);
            }else if(xhr.status===415){
                alert("no messages");
            }else{
                alert("error " + xhr.status);
            }
        }
    };
    xhr.open('GET', 'GetMessages?client=' + client, false);
    xhr.send();
    return msg;
}

//checks if the user can send messages to the doctor aka if the user has completed randevouz with this doc
function canSendMsg(index){
    var doc_id=docs[index].doctor_id;
    var user_id;
    getCurrentUser();
    user_id=loggedInUser.user_id;
    loggedInUser=null;
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status!==200 && xhr.status!==413){
                alert("error " + xhr.status);
            }
        }
    };
    xhr.open('GET', 'CanSendMessage?doctor_id=' + doc_id + '&user_id=' + user_id, false);
    xhr.send();
    return xhr.status;
}

//gets all the doctor randevouz
function getDocsRandevouz(doc_id){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                randevouz=JSON.parse(xhr.responseText);
            }else if(xhr.status===411){
                randevouz=null;
            }else{
                alert("error " + xhr.status);
            }
        }
    };
    xhr.open('GET', 'GetDoctorRandevouz?doctor_id=' + doc_id , false);
    xhr.send();
    return xhr.status;
}

function postRandevouz(){
    var params="";
    params=formToParams(document.getElementById('randevouz-form'));
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("randevouze added!");
                document.getElementById('randevouz-form').reset();
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'AddRandevouz?' + params, false);
    xhr.send();

}


function doneRandevouz(){
    var rndvId=event.srcElement.id.split('_')[1];       //the randevou id
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("done");
            }else{
                alert("error " + xhr.status);
            }
        }
    };

        document.getElementById(event.srcElement.id).parentElement.parentElement.style.display='none';
    xhr.open('GET', 'DoneRandevouz?randevouz_id=' + rndvId, false);
    xhr.send();
}


/**
 * returns the clients of the doctor with done randevouz
 */
function getDoctorsClients(){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                clients=JSON.parse(xhr.responseText);
            }else if(xhr.status===411){
                clients=null;
            }else{
                alert("error " + xhr.status);
                clients=null;
            }
        }
    };

    xhr.open('GET', 'GetDoctorClients', false);
    xhr.send();
}


function postTreatment(form){
    var bt_id=form.split('_')[1];
    form=document.getElementById(form);
    var params=formToParams(form);

    params+='&bloodtest_id=' + bt_id;

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert("added!")
            }else{
                //alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'AddTreatment?' + params, false);
    xhr.send();
}