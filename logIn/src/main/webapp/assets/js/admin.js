var users=[];           //contains all the users as an array of objects
var docs=[];            //contains all the doctors as an array of objects
var userContainer;      //contains the users and doctors buttons
var seeUsers;           //button to display users
var seeDocs;            //button to display doctors
var adminsBackBtn;      //the back button to navigate the admins page
var certfBtn;   //the button to certificate the doc
window.addEventListener('load', ()=>{
    adminsBackBtn=document.createElement('button');
    adminsBackBtn.style.backgroundColor='var(--my_green)';
    adminsBackBtn.innerHTML="Back";

    userContainer=document.createElement('div');  //contains the buttons for the users
    userContainer.setAttribute('class', 'user-btn-container');

    seeUsers=document.createElement('button');           //button to display users
    seeDocs=document.createElement('button');            //button to display doctors

    seeUsers.innerHTML="Manage Users";
    seeDocs.innerHTML="Manage Doctors";

    certfBtn=document.createElement('button');
    certfBtn.addEventListener('click', certificateDocs);
    certfBtn.style.backgroundColor='var(--my_yellow)';
    certfBtn.innerHTML='Certify';

    seeUsers.addEventListener('click', ()=>{
        createUserBtnList('su');        //call the function create user list for simple users
    });
    seeDocs.addEventListener('click', ()=>{
        createUserBtnList('doc')    //call the function create user list for docs
    });

});

/*displays admin's index page*/
function showAdmin(){
    getCurrentUser();   //get users data

    main.innerHTML="";
    columnBtnContainer.innerHTML="";
    showGreetingMsg(loggedInUser.username);

    columnBtnContainer.append(seeUsers);
    columnBtnContainer.append(seeDocs);
    main.append(columnBtnContainer);
    loggedInUser=null;          //destroy the data after use. (for security)
}

/*creates a table with the users/docs as buttons
* usertype: su for simple users, doc for doctors*/
function createUserBtnList(userType){
    var i;
    var h3=document.createElement('h3');
    var isListEmpty;        //true if the users'/docs' list is empty, false if not
    var listLength;        //the length of the users/docs list


    btnList=[];             //every time show admin is called delete all values from array to avoid duplicate
    userContainer.innerHTML="";     //avoid duplicate
    main.innerHTML="";

    switch (userType){
        case 'su':
            getUsers(); //call the servlet to get the users records
            h3.innerHTML="Users";
            isListEmpty=(users===null || users.length===0);
            if(isListEmpty)
                listLength=0;
            else {
                listLength=users.length;
                sortArrayPart(users, 0, listLength);
            }
            break;
        case 'doc':
            getDocs(); //call the servlet to get the docs records
            h3.innerHTML="Doctors";
            isListEmpty=(docs===null || docs.length===0);
            if(isListEmpty)
                listLength=0;
            else{
                listLength=docs.length;
                //first the not certified docs
                placeNotCrtfFirst();

                var start=0;
                var length=docs.length;

                for(var j=0; j<docs.length; j++){
                    if(docs[j].certified==1){
                        length=j;
                        break;
                    }
                }
                //the sort the array
                sortArrayPart(docs, start, length);

                start=length;
                length=docs.length-start;

                sortArrayPart(docs, start, length);
            }
            break;
    }

    //remove all other listeners to this btn
    adminsBackBtn.removeEventListener('click', createUserBtnList);       //avoid multiple listeners

    adminsBackBtn.addEventListener('click', showAdmin);   //show again admins index page

    if(isListEmpty){
        var h2=document.createElement('h2');
        h2.innerHTML="No records!";
        h2.style.marginLeft='auto';
        h2.style.marginRight='auto';
        userContainer.append(h2);
        userContainer.style.overflow='hidden';
    }else{
        userContainer.style.overflowY='scroll';

        //create a button for every user or doc to access their data
        for(i=0; i<listLength; i++){
            var btn=document.createElement('button');
            btn.setAttribute('id', userType + "_" + i);     //the id helps to find the correct array and the correct user to print
            switch (userType){
                case 'su':
                    btn.innerHTML=users[i].username;
                    break;
                case 'doc':
                    btn.innerHTML=docs[i].username;
                    if(docs[i].certified==0){
                        btn.style.backgroundColor='red';
                        btn.style.color='white'
                    }
                    break;
            }
            btn.addEventListener('click', printUserData);
            btnList.push(btn);
        }
    }

    rowBtnContainer.innerHTML="";  //clear the old buttons
    rowBtnContainer.append(adminsBackBtn);

    msg.innerHTML="";

    msg.append(h3);         //"Users" msg
    main.append(msg);

    main.append(userContainer);
    main.append(rowBtnContainer);

    //append buttons in container
    for(i=0; i<btnList.length; i++)
        userContainer.append(btnList[i]);

}


//a listener to the users buttons. Prints the user's/doc's data
function printUserData(){
    var tbl;            //a table
    var tRow;           //a table row
    var tHeader;        //a table header
    var tData;          //table data tag
    var keys=[];           //the keys of an object
    var values=[];         //the values of an object

    var tokens=event.srcElement.id.split("_");
    var userType=tokens[0];         //the user type
    var index=parseInt(tokens[1]);  //the index for the user's information inside the users array is the btn's id

    main.innerHTML="";
    rowBtnContainer.innerHTML=""; //clear from old buttons
    rowBtnContainer.append(adminsBackBtn);
    rowBtnContainer.append(delBtn);


    delBtn.setAttribute('id', index);   //this way del btn knows the user to delete

    //remove all other listeners to this btn
    adminsBackBtn.removeEventListener('click', showAdmin);       //avoid multiple listeners

    switch (userType){
        case 'su':
            adminsBackBtn.addEventListener('click', ()=>{
               createUserBtnList('su');
            });

            delBtn.removeEventListener('click', deleteDoc) //remove listener to avoid deleting other records
            delBtn.addEventListener('click', deleteUser);
            keys=Object.keys(users[index]);
            values=Object.values(users[index]);
            break;
        case 'doc':
            adminsBackBtn.addEventListener('click', ()=>{
                createUserBtnList('doc');
            });
            delBtn.removeEventListener('click', deleteUser) //remove listener to avoid deleting other records
            delBtn.addEventListener('click', deleteDoc);
            keys=Object.keys(docs[index]);
            values=Object.values(docs[index]);

            if(docs[index].certified===0) {
                rowBtnContainer.append(certfBtn);
                certfBtn.setAttribute('id', index);     //the id is the index for the docs' object array
            }
            break;
    }

    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');
    tbl.setAttribute('class', 'users-table');
    //create the table with the user's data
    main.append(tbl);
    for(var i=0; i<keys.length; i++){
        tRow=document.createElement('tr');
        tHeader=document.createElement('th');
        tData=document.createElement('td');

        tData.setAttribute('id', 'tData_' + keys[i]);  //add ids in order to refresh the data

        tHeader.innerHTML=keys[i];
        tData.innerHTML=values[i];

        tRow.append(tHeader);
        tRow.append(tData);
        tbl.append(tRow);
    }

    main.append(rowBtnContainer);
}


//this functions sorts the docs array placing the not certified docs first
function placeNotCrtfFirst(){
    var nextPosition=0;
    var i;
    var temp;
    for(i=0; i<docs.length; i++){
        if(docs[i].certified==0){
            temp=docs[nextPosition];
            docs[nextPosition]=docs[i];
            docs[i]=temp;
            nextPosition++;
        }
    }
    return;
}

//This function sort a sub array starting from index <start> with length <length>
function sortArrayPart(array, start, length){
    var temp;
    for(var i=0; i<length-1; i++){
        for(var j=0; j<length-i-1; j++){
            if(array[j+start].username>array[j+start+1].username){
                temp=array[j+start];
                array[j+start]=array[j+start+1];
                array[j+start+1]=temp;
            }
        }
    }
    return;
}


/*retrieves the docs from the database*/
function getDocs(){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                docs=JSON.parse(xhr.responseText)
            }else{
                alert(xhr.status)
            }
        }
    };

    xhr.open('GET', 'GetDoctors', false);
    xhr.send();
}


/*retrieves the users from the database*/
function getUsers(){
    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200) {
                users=JSON.parse(xhr.responseText)
            }else{
                alert(xhr.status)
            }
        }
    };

    xhr.open('GET', 'GetUsers', false);
    xhr.send();
}

/*this servlet is called by the delete button and deletes the user*/
function deleteUser(){
    var index=event.srcElement.id;  //the index for the user's information inside the users array is the btn's id
    var uname=users[index].username;        //the username of the user to be deleted

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            alert(xhr.status);
        }
    };

    xhr.open('GET', 'DeleteUser?username=' + uname, false);
    xhr.send();
    createUserBtnList("su");

}

/*this servlet is called by the delete button and deletes the user*/
function deleteDoc(){
    var index=event.srcElement.id;  //the index for the user's information inside the users array is the btn's id
    var uname=docs[index].username;        //the username of the user to be deleted

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            alert(xhr.status);
        }
    };

    xhr.open('GET', 'DeleteDoc?username=' + uname, false);
    xhr.send();
    createUserBtnList("doc");
}

function certificateDocs(){
    var index=event.srcElement.id;
    var uname=docs[index].username

    const xhr = new XMLHttpRequest();
    xhr.onload=function(){
        if(xhr.readyState===4){
            if(xhr.status===200){
                rowBtnContainer.innerHTML="";
                rowBtnContainer.append(adminsBackBtn)
                rowBtnContainer.append(delBtn);
                document.getElementById('tData_certified').innerHTML='1';
                alert(xhr.status + "certified!");
            }else{
                alert("error " + xhr.status);
            }
        }
    };

    xhr.open('GET', 'CertifyDoc?username=' + uname, false);
    xhr.send();
}