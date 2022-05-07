var loggedInUser=[];    //contains the current users data
var main;       //the main profile section
var btnList=[];
var msg;
var delBtn;   //the delete user button
var certfBtn;   //the button to certificate the doc
var rowBtnContainer;       //div that contains buttons in a row
var columnBtnContainer;    //div that contains buttons in a column
var userType;   //contains the status code that decodes the userType

/*make initializations*/
window.addEventListener('load', ()=>{
    document.getElementById('logout-btn').addEventListener('click', logOut);

    main=document.getElementById('user-profile-container');
    msg=document.createElement('div');                 //contains a msg

    rowBtnContainer=document.createElement('div');
    columnBtnContainer=document.createElement('dic');

    delBtn=document.createElement('button');
    certfBtn=document.createElement('button');

    msg.setAttribute('class', 'msg');
    rowBtnContainer.setAttribute('class', 'row-btn-container');
    columnBtnContainer.setAttribute('class', 'col-btn-container');

    delBtn.style.backgroundColor='red';
    certfBtn.style.backgroundColor='var(--my_yellow)';

    certfBtn.addEventListener('click', certificateDocs);

    delBtn.innerHTML='Delete';
    certfBtn.innerHTML='Certify';

    document.getElementById('settings-btn').addEventListener('click', settings);
});

//listener for the settings btn. argument the user object
function settings(){
    var returnBtn=document.createElement('button');
    var changeBtn=document.createElement('button');
    var keys=[];
    var values=[];
    var tRow;
    var tData;
    var tbl;
    var tHeader;
    var input;
    var form=document.createElement('form');   //the data will be displayed in a form

    getCurrentUser();

    rowBtnContainer.innerHTML="";       //remove all previous buttons
    main.innerHTML="";
    returnBtn.innerHTML='Start';
    changeBtn.innerHTML='Change';

    returnBtn.style.backgroundColor='var(--my_green)';
    changeBtn.style.backgroundColor='var(--my_yellow)';
    changeBtn.addEventListener('click', updateData);

    form.setAttribute('id', 'update-form');

    //make the return button to return to the main page of the user
    if(userType===201){   //the user is simple
         returnBtn.addEventListener('click', showUser);
    }else if(userType===202){      //its doctor
         returnBtn.addEventListener('click', showDoctor);
    }else{   //its admin
        returnBtn.addEventListener('click', showAdmin);
    }

    keys=Object.keys(loggedInUser);
    values=Object.values(loggedInUser);

    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');
    tbl.setAttribute('class', 'settings-table');
    //create the table with the user's data
    main.append(tbl);
    for(var i=0; i<keys.length; i++){
        //dont display id, lat, lon and certified for docs
        if(keys[i].includes('id') || keys[i]==="lat" || keys[i]==="lon" || keys[i]==="certified")
            continue;

        tRow=document.createElement('tr');
        tHeader=document.createElement('th');
        input=document.createElement('input');
        tData=document.createElement('td');

        if(keys[i]!=='username' && keys[i]!=='amka'){       //all data except amka and uname can be changed
            input.setAttribute('id', 'tData_' + keys[i]);
            input.setAttribute('value', values[i]);
            input.setAttribute('name', keys[i]);
            tData.append(input);
        }else{
            tData.setAttribute('id', 'tData_' + keys[i]);  //add ids in order to refresh the data
            tData.innerHTML=values[i];
        }

        tHeader.innerHTML=keys[i];

        tRow.append(tHeader);
        tRow.append(tData);
        tbl.append(tRow);
    }

    //use the back button and the change button
    rowBtnContainer.append(returnBtn);
    rowBtnContainer.append(changeBtn);
    form.append(tbl);
    main.append(form);
    main.append(rowBtnContainer)
}

/*displays a greeting message*/
function showGreetingMsg(name){
    var h1=document.createElement('h1');
    h1.innerHTML="Hello " + name + "!";
    msg.innerHTML="";
    msg.append(h1);
    main.append(msg);
}

//displays the users profile
//status 201 simple user
//status 202 doctor
//status 203 admin
function displayProfile(status){
    //hide the login form
    document.getElementById('login-container').style.display='none';
    document.getElementById('logout-btn').style.display='block';
    document.getElementById('settings-btn').style.display='block';
    userType=status;  //store the user type

    if(status===202){
        //display doctors profile
        console.log('its doc')
        showDoctor();
    }else if(status===201){
        //display users profile
        console.log('its su!')
        showUser();
    }else{
        //display admin page
        console.log('su')
        showAdmin();
    }
}


//returns to the main page
function logOut(){
    docs=[];
    users=[];
    loggedInUser=[];

    //delete the users profile from the page
    document.getElementById('user-profile-container').innerHTML="";
    //and return to the login page
    document.getElementById('login-container').style.display='block';
    //hide again the log out btn
    document.getElementById('logout-btn').style.display='none';
    //hide the settings btn
    document.getElementById('settings-btn').style.display='none';
}


//posts data to the servlet to login
function logIn(){
    var params="";
    var output=document.getElementById('output');

    params=formToParams(document.getElementById('form'));
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===201 || xhr.status===202 || xhr.status===203){
                output.innerHTML="";
                Swal.fire({
                    title: 'Hi!',
                    text: 'You have been logged in!',
                    icon: 'success',
                    confirmButtonText: 'Visit my profile'
                }).then(function (){
                    displayProfile(xhr.status);
                });
            }else if(xhr.status===405){
                output.innerHTML="There is no user with this username";
            }else if(xhr.status===406){
                output.innerHTML="Incorrect password.<br>Try again or contact us!";
            }else if(xhr.status===409){
                output.innerHTML="Wait for the admin to certify you in order to log in :)";
            }else{
                Swal.fire({
                    title: 'Opsss',
                    text: 'An unexpected error occurred!(er code: ' + xhr.status + ')',
                    icon: 'error',
                    confirmButtonText: 'Try again!'
                });
            }
        }
    };

    //params="username=admin&password=admin12*"
   // params="username=alex&password=alex!1"
    xhr.open('POST', 'LogIn');
    xhr.send(params);
}


//returns the data of the logged in user
function getCurrentUser(){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                loggedInUser=JSON.parse(xhr.responseText);
            }else if(xhr.status===408){   //time expired
                logOut();
            }else{
                alert(xhr.status);
            }
        }
    };

    xhr.open('GET', 'GetCurrentUser', false);
    xhr.send();
}


//updates the database using the new data from the form
function updateData(){
    var params="";
    var username=document.getElementById('tData_username').innerHTML;
    params=formToParams(document.getElementById('update-form'))
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                alert('data changed!');
            }else{
                alert('error ' + xhr.status);
            }
        }
    };

    xhr.open('POST', 'UpdateData', false);
    xhr.send(params);

}

//takes a form and returns the params query string
function formToParams(form){
    let formData=new FormData(form);
    var params="";
    for (let [name, value] of formData) {
        params+=name + "=" + value + "&";
    }
    params=params.substring(0, params.length - 1);
    return params;
}

function clearForm(form){
    form.reset();
}