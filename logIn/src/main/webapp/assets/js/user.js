var addExmsBtn;     //button to add exams
var showExmsBtn;    //button to see the exams
var viewRandevouz   //button to see the randevouz
var findDocsBtn;    //button for finding doctors
var viewTrtBtn;     //button for showing all the active treatments
var trtObj=[];      //contains a treatment object received from a servlet
var docsContainer;  //contains the docs as btns
var noRandevouzContainer;     //contains the msg no randevouz
var noTrtContainer;           //contains the msg no treatments for this user

window.addEventListener('load', ()=>{

    noRandevouzContainer=document.createElement('div');
    noRandevouzContainer.setAttribute('class', 'msg-container');

    noTrtContainer=document.createElement('div');
    noTrtContainer.setAttribute('class', 'msg-container');

    docsContainer=document.createElement('div');  //contains the buttons for the users
    docsContainer.setAttribute('class', 'user-btn-container');

    addExmsBtn=document.createElement('button');
    showExmsBtn=document.createElement('button');
    viewRandevouz=document.createElement('button');
    viewTrtBtn=document.createElement('button');
    findDocsBtn=document.createElement('button');

    addExmsBtn.addEventListener('click', addExams);
    showExmsBtn.addEventListener('click', showExams);
    viewRandevouz.addEventListener('click', showUsersRandevouz);
    findDocsBtn.addEventListener('click', findDocs);
    viewTrtBtn.addEventListener('click', showActiveTreatments);

    addExmsBtn.innerHTML="Add Exams";
    showExmsBtn.innerHTML="View Exams";
    viewRandevouz.innerHTML="View Randevouz";
    findDocsBtn.innerHTML="Find Doctors";
    viewTrtBtn.innerHTML="View Treatments";
});

function showUser(){
    getCurrentUser();       //update the current user
    main.innerHTML="";
    columnBtnContainer.innerHTML="";
    //add the users buttons
    showGreetingMsg(loggedInUser.username);

    columnBtnContainer.append(addExmsBtn);
    columnBtnContainer.append(showExmsBtn);
    columnBtnContainer.append(findDocsBtn);
    columnBtnContainer.append(viewTrtBtn);
    columnBtnContainer.append(viewRandevouz);
    main.append(columnBtnContainer);
    loggedInUser=null;          //destroy the data after use. (for security)
}

function setBackBtn(listener){
    var btn;
    btn=document.createElement('button');
    btn.innerHTML="Back";
    btn.addEventListener('click', listener);
    btn.style.backgroundColor='var(--my_green)';
    return btn;
}

//shows a form and the users adds exams
function addExams(){
    const inputNames=["test_date", "medical_center", "blood_sugar", "cholesterol", "iron", "vitamin_d3", "vitamin_b12"];
    var backBtn;
    var form=document.createElement('form');   //the form to enter the exams
    var sbmtBtn=document.createElement('button');    //the submit button
    var input;
    var tbl;
    var tRow;
    var tData;
    var tHeader;

    backBtn=setBackBtn(showUser);
    tbl=document.createElement('table');
    form.setAttribute('id', 'exams-form');
    form.setAttribute('onsubmit', 'postExams(); return false;');
    sbmtBtn.setAttribute('type', 'submit');
    sbmtBtn.setAttribute('class', 'submit-btn');
    sbmtBtn.innerHTML="Submit";

    for(var i=0; i<inputNames.length; i++){
        tRow=document.createElement('tr');
        tHeader=document.createElement('th');
        input=document.createElement('input');
        tData=document.createElement('td');

        input.setAttribute("required", "");
        input.setAttribute('name', inputNames[i]);
        if(i===0){   //type date
            input.setAttribute('type', 'date');
        }else if(i>=2){   //type float
            input.setAttribute('type', 'number');
            input.setAttribute('step', '0.01');
        }
        tData.append(input);

        tHeader.innerHTML=inputNames[i];

        tRow.append(tHeader);
        tRow.append(tData);
        tbl.append(tRow);
    }
    tRow=document.createElement('tr');
    tHeader=document.createElement('th');
    tHeader.setAttribute("colspan", "2");
    tHeader.append(sbmtBtn);
    tRow.append(tHeader);
    tbl.append(tRow);

    form.style.marginTop='130px';
    form.append(tbl);
    main.innerHTML="";
    main.append(form);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);

    main.append(rowBtnContainer);
}

//displays all the exams
function showExams(){
    var tbl;
    var tRow;
    var tData;
    var th;
    var tHead;
    var tBody;
    const headers=["Date", "Center", "Iron", "Cholesterol", "Sugar", "Vitamin B12", "Vitamin D3",
        "Iron Level", "Cholesterol Level", "Sugar Level", "Vitamin B12 Level", "vitamin D3 Level", "Treatment"];
    const keys=["test_date", "medical_center", "iron", "cholesterol", "blood_sugar", "vitamin_b12",
        "vitamin_d3", "iron_level", "cholesterol_level", "blood_sugar_level", "vitamin_b12_level", "vitamin_d3_level"];
    var div;
    var i;
    var trtBtn;     //the button for the treatment
    var backBtn;

    getCurrentUser();

    getExams(loggedInUser.amka);

    loggedInUser=null;  //destroy

    sortExamsByDate();

    //the table
    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');

    //the table head
    tHead=document.createElement('thead');

    //the table body
    tBody=document.createElement('tbody');

    //the table container
    div=document.createElement('div');
    div.setAttribute("class", 'scroll-container');

    //create the headers
    //create the row for the headers
    tRow=document.createElement('tr');
    for(i=0; i<headers.length; i++){
        th=document.createElement('th');
        th.innerHTML=headers[i];
        tRow.append(th);
    }
    tHead.append(tRow);

    //create the body
    for(i=0; i<exams.length; i++){
        tRow=document.createElement('tr');
        for(var j=0; j<keys.length; j++){
            tData=document.createElement('td');
            tData.innerHTML=(exams[i][keys[j]]);   //found on internet that obj.name is equivalent to obj['name']
            tRow.append(tData);
        }
        //add the treatment btn
        tData=document.createElement('td');
        trtBtn=document.createElement('button');
        trtBtn.setAttribute('id', 'treatment_' + exams[i].bloodtest_id); //the id helps to display the correct treatment
        trtBtn.addEventListener('click', ()=>{
            showTreatment(showExams);
        });
        trtBtn.innerHTML='Treatment';
        tData.append(trtBtn);
        tRow.append(tData);
        tBody.append(tRow);
    }

    tbl.append(tHead);
    tbl.append(tBody);
    div.append(tbl);
    main.innerHTML="";
    main.append(div);

    backBtn=setBackBtn(showUser);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    main.append(rowBtnContainer);

}

//sorts the users bloodtest by the date
function sortExamsByDate(){
    var temp;
    for(var i=0; i<exams.length-1; i++){
        for(var j=0; j<exams.length-i-1; j++){
            if(parseInt(exams[j].test_date.split('-')[0])<parseInt(exams[j+1].test_date.split('-')[0])){
                temp=exams[j];
                exams[j]=exams[j+1];
                exams[j+1]=temp;
            }else if(parseInt(exams[j].test_date.split('-')[0])===parseInt(exams[j+1].test_date.split('-')[0])){
                if(parseInt(exams[j].test_date.split('-')[1])<parseInt(exams[j+1].test_date.split('-')[1])){
                    temp=exams[j];
                    exams[j]=exams[j+1];
                    exams[j+1]=temp;
                }else if(parseInt(exams[j].test_date.split('-')[1])===parseInt(exams[j+1].test_date.split('-')[1])){
                    if(parseInt(exams[j].test_date.split('-')[2])<parseInt(exams[j+1].test_date.split('-')[2])){
                        temp=exams[j];
                        exams[j]=exams[j+1];
                        exams[j+1]=temp;
                    }
                }
            }
        }
    }
    return;
}

//shows the doctors as a list of buttons using some of the admins function
function findDocs(){
    var i;
    var length;
    var backBtn;

    backBtn=setBackBtn(showUser);

    getDocs();

    //TODO sort docs array
    main.innerHTML="";
    docsContainer.innerHTML="";
    btnList=[];             //every time show admin is called delete all values from array to avoid duplicate

    length=docs.length;

    //create a button for every doc

    for(i=0; i<length; i++){
        if(docs[i].certified==0)
            continue;

        var btn=document.createElement('button');
        btn.setAttribute('id', "doc_" + i);     //the id helps to find the correct array and the correct user to print
        btn.innerHTML=docs[i].username;
        btn.addEventListener('click', displayDocsInfo);
        btnList.push(btn);
    }


    for(i=0; i<btnList.length; i++)
        docsContainer.append(btnList[i]);

    if(docs===null || length===0 || btnList.length===0){        //no docs or no certf docs
        var h2=document.createElement('h2');
        h2.innerHTML="No doctors available!";
        h2.style.marginLeft='auto';
        h2.style.marginRight='auto';
        docsContainer.append(h2);
        docsContainer.style.overflow='hidden';
    }


    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    main.append(docsContainer);
    main.append(rowBtnContainer);
}


function showMessagePage(){
    var index=event.srcElement.id.split('_')[1];
    var backBtn;

    backBtn=setBackBtn(displayDocsInfo);
    backBtn.setAttribute('id', 'doc_' + index);

    main.innerHTML="";
    rowBtnContainer.innerHTML="";

    var text=document.createElement('textarea');
    var div=document.createElement('div');
    var sendBtn=document.createElement('button');

    div.setAttribute('class', 'textarea-container');

    text.setAttribute('id', 'message-area');
    text.setAttribute('cols', '50');
    text.setAttribute('rows', '10');

    sendBtn.innerHTML='Send';
    sendBtn.style.backgroundColor='var(--my_yellow)'

    sendBtn.setAttribute('id', 'doc_' + docs[index].doctor_id);
    sendBtn.addEventListener('click', sendMessageClientToDoc);

    rowBtnContainer.append(backBtn);
    rowBtnContainer.append(sendBtn);

    div.append(text);

    main.append(div);
    main.append(rowBtnContainer)
}

//displays the doctors information and randevouz to the user
function displayDocsInfo(){
    var index=event.srcElement.id.split('_')[1];      //the id of the button is the index of the doctor in the array
    var div=document.createElement('div');
    var p=document.createElement('p');
    var h2;
    var selectBtn;
    var sendMsgBtn=document.createElement('button');        //button to send msg to doctor
    var backBtn;
    main.innerHTML="";

    sendMsgBtn.style.backgroundColor='var(--my_yellow)'
    sendMsgBtn.innerHTML="Msg";
    sendMsgBtn.setAttribute('id', 'doc_' + index);  //to find doctor
    sendMsgBtn.addEventListener('click', showMessagePage);

    div.setAttribute('class', 'doc-info-container');

    p.innerHTML="Address: " + docs[index].address;

    div.append(p);
    p=document.createElement('p');
    p.innerHTML="Tel: " + docs[index].telephone;
    div.append(p);

    p=document.createElement('p');
    p.innerHTML="Email: " + docs[index].email;
    div.append(p);
    main.append(div);


    rowBtnContainer.innerHTML="";
    backBtn=setBackBtn(findDocs);

    rowBtnContainer.append(backBtn);

    //can send message only if the user had randevouz with the doc
    if(canSendMsg(index)===200)
        rowBtnContainer.append(sendMsgBtn);

    getFreeRandevouz(docs[index].doctor_id, randevouz);    //free randevouz now in object array randevouz

    //show no randevouz message
    if(randevouz===null){
        h2=document.createElement('h2');
        h2.innerHTML="No free randevouz for this doctor!";
        noRandevouzContainer.innerHTML="";
        noRandevouzContainer.append(h2);
        main.append(noRandevouzContainer);

    }else{  //display randevouz
        const headers=["Date", "Price", "Book"];  //, "Doc Info", "User Info"];
        const keys=["date_time", "price"] //, "doctor_info", "user_info"];
        var tbl=document.createElement('table');
        var tData, th, tRow, tHeader, tBody;

        div=document.createElement('div');
        div.setAttribute("class", 'scroll-container');
        div.style.width='30%';

        //create the table
        //create the headers
        tHeader=document.createElement('thead');
        tRow=document.createElement('tr');
        for(var i=0; i<headers.length; i++){
            th=document.createElement('th');
            th.innerHTML=headers[i];
            tRow.append(th);
        }
        tHeader.append(tRow);

        //create the tBody
        tBody=document.createElement('tbody');
        for(i=0; i<randevouz.length; i++){
            tRow=document.createElement('tr');
            for(var j=0; j<keys.length; j++){
                tData=document.createElement('td');
                tData.innerHTML=(randevouz[i][keys[j]]);
                tRow.append(tData);
            }
            //add the select randevou btn
            tData=document.createElement('td');
            selectBtn=document.createElement('button');
            selectBtn.innerHTML="Select";
            selectBtn.setAttribute('id', 'selectRndvz_' + randevouz[i].randevouz_id);
            selectBtn.addEventListener('click', bookRandevouz);
            tData.append(selectBtn);
            tRow.append(tData);
            tBody.append(tRow)
        }
        tbl.append(tHeader);
        tbl.append(tBody);
        div.append(tbl);
        main.append(div);
    }
    main.append(rowBtnContainer);
}


//function displays all users randevouz
function showUsersRandevouz(){
    var userId;
    var h2, div;
    const headers=["Date", "Doctor Info", "Price", "Cancel"];
    const keys=["date_time", "doctor_info", "price"];
    var backBtn;

    backBtn=setBackBtn(showUser);
    getCurrentUser();

    userId=loggedInUser.user_id;
    loggedInUser=null;
    randevouz=null;
    getBookedRandevouz(userId, randevouz);
    main.innerHTML="";
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);

    if(randevouz===null || randevouz.length===0){
        h2=document.createElement('h2');
        h2.innerHTML="You have no randevouze!";
        noRandevouzContainer.innerHTML="";
        noRandevouzContainer.append(h2);
        main.append(noRandevouzContainer);
        main.append(rowBtnContainer);
        return;
    }
    //else display the randevouz
    div=createRandevouzTable(headers, keys);

    main.append(div);
    main.append(rowBtnContainer);
}


//displays all the active treatments
function showActiveTreatments(){
    var tHeader, tBody, tRow, tData, tbl, th;
    var div;
    const headers=["Start", "End", "Treatment"];
    const keys=["start_date", "end_date", "treatment_text"];
    var backBtn;

    tbl=document.createElement('table');
    tbl.setAttribute('border', '1px');

    div=document.createElement('div');
    div.setAttribute("class", 'scroll-container');
    div.style.width='50%'

    main.innerHTML="";
    rowBtnContainer.innerHTML="";
    backBtn=setBackBtn(showUser);
    rowBtnContainer.append(backBtn);

    getCurrentUser();
    getUserTreatment(loggedInUser.user_id, trtObj);
    loggedInUser=null;

    if(trtObj===null || trtObj.length===0){                    //no active treatment for the user
        var h2=document.createElement('h2');
        h2.innerHTML="No active treatments!";
        h2.style.marginLeft='auto';
        h2.style.marginRight='auto';
        noTrtContainer.innerHTML="";
        noTrtContainer.append(h2);

        main.append(noTrtContainer);
        main.append(rowBtnContainer);
        return;
    }
    //create the table
    //create the headers
    tHeader=document.createElement('thead');
    tRow=document.createElement('tr');
    for(var i=0; i<headers.length; i++){
        th=document.createElement('th');
        th.innerHTML=headers[i];
        tRow.append(th);
    }
    tHeader.append(tRow);

    //create the tBody
    tBody=document.createElement('tbody');
    for(i=0; i<trtObj.length; i++){
        tRow=document.createElement('tr');
        for(var j=0; j<keys.length; j++){
            tData=document.createElement('td');
            tData.innerHTML=(trtObj[i][keys[j]]);
            tRow.append(tData);
        }
        tBody.append(tRow);
    }

    tbl.append(tHeader);
    tbl.append(tBody);
    div.append(tbl);
    main.append(div);
    main.append(rowBtnContainer);
}