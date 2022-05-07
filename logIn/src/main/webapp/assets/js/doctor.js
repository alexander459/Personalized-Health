var clients=[];
var clientsContainer;  //contains the clients as btns

window.addEventListener('load', ()=>{
    clientsContainer=document.createElement('div');  //contains the buttons for the users
    clientsContainer.setAttribute('class', 'user-btn-container');
});

function showDoctor(){
    var manageRndvBtn;     //button to add exams
    var seeClientsBtn;       //shows the clients of the doctor
    main.innerHTML="";

    getCurrentUser();       //update the current user

    showGreetingMsg(loggedInUser.username);
    loggedInUser=null;  //destroy

    columnBtnContainer.innerHTML="";
    manageRndvBtn=document.createElement('button');
    manageRndvBtn.innerHTML="Manage Randevouz";
    manageRndvBtn.addEventListener('click', manageRandevouz);

    seeClientsBtn=document.createElement('button');
    seeClientsBtn.innerHTML='See Clients';
    seeClientsBtn.addEventListener('click', seeClients);

    columnBtnContainer.append(manageRndvBtn);
    columnBtnContainer.append(seeClientsBtn);
    main.append(columnBtnContainer);
}

/**
 * displays the clients as a buttonlist
 */
function seeClients(){
    var backBtn, i;

    main.innerHTML="";

    backBtn=setBackBtn(showDoctor);
    getDoctorsClients();

    //btnList=[]; //clear the button list
    clientsContainer.innerHTML="";
    rowBtnContainer.innerHTML="";

    if(clients===null || clients.length===0){        //no clients
        var h2=document.createElement('h2');
        h2.innerHTML="No clients!";
        h2.style.marginLeft='auto';
        h2.style.marginRight='auto';
        clientsContainer.append(h2);
        clientsContainer.style.overflow='hidden';
        rowBtnContainer.append(backBtn);
        main.append(clientsContainer);
        main.append(rowBtnContainer);
        return;
    }

    for(i=0; i<clients.length; i++){
        var btn=document.createElement('button');
        btn.setAttribute('id', "client_" + clients[i]);
        btn.innerHTML=clients[i];
        btn.addEventListener('click', displayClientInfo);
        clientsContainer.append(btn);
    }


    rowBtnContainer.append(backBtn);
    main.append(clientsContainer);
    main.append(rowBtnContainer);
}



function displayClientInfo(){
    var client=event.srcElement.id.split('_')[1];
    var examsBtn;       //btn to see clients exams
    var msgBtn;         //btn to send message
    var backBtn=setBackBtn(seeClients);

    main.innerHTML="";
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    columnBtnContainer.innerHTML="";

    examsBtn=document.createElement('button');
    msgBtn=document.createElement('button');

    examsBtn.setAttribute('id', 'exams_' + client);
    msgBtn.setAttribute('id', 'msg_' + client);

    examsBtn.addEventListener('click', seeExams);
    msgBtn.addEventListener('click', msgPage);
    examsBtn.innerHTML="Exams";
    msgBtn.innerHTML="Message";

    columnBtnContainer.append(examsBtn);
    columnBtnContainer.append(msgBtn);

    main.append(columnBtnContainer);
    main.append(rowBtnContainer);
}

function msgPage(){
    var client=event.srcElement.id.split('_')[1];
    var backBtn;
    var messages=[];
    messages=getMessagesFromClient(client);


    main.innerHTML="";

    var tbl;
    var tRow;
    var tData;
    var th;
    var tHead;
    var tBody;
    const headers=["Sender", "Date", "Blood Type", "Donator", "Message"];
    const keys=["sender", "date_time", "bloodtype", "blood_donation", "message"];
    var div;
    var i;

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
    for(i=0; i<messages.length; i++){
        tRow=document.createElement('tr');
        for(var j=0; j<keys.length; j++){
            tData=document.createElement('td');
            tData.innerHTML=(messages[i][keys[j]]);   //found on internet that obj.name is equivalent to obj['name']
            tRow.append(tData);
        }
        tbl.append(tRow);
    }

    tbl.append(tHead);
    tbl.append(tBody);
    div.append(tbl);

    main.append(div);

    backBtn=setBackBtn(seeClients);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    main.append(rowBtnContainer);

}

/**
 * obtains the clients bloodtests
 */
function seeExams(){
    console.log()
    var client=event.srcElement.id.split('_')[1];
    var div, h3, backBtn;
    var tbl, tRow, tData, th, tHead, tBody;
    var i, trtBtn;
    const headers=["AMKA", "Date", "Center", "Iron", "Cholesterol", "Sugar", "Vitamin B12", "Vitamin D3",
        "Iron Level", "Cholesterol Level", "Sugar Level", "Vitamin B12 Level", "vitamin D3 Level", "Treatment", "Edit"];
    const keys=["amka", "test_date", "medical_center", "iron", "cholesterol", "blood_sugar", "vitamin_b12",
        "vitamin_d3", "iron_level", "cholesterol_level", "blood_sugar_level", "vitamin_b12_level", "vitamin_d3_level"];

    main.innerHTML='';

    backBtn=setBackBtn(seeClients);
    rowBtnContainer.innerHTML='';
    rowBtnContainer.append(backBtn);

    getClientsExams(client);

    if (exams===null){
        div=document.createElement('div');
        div.setAttribute('class', 'msg');
        h3=document.createElement('h3');
        h3.innerHTML="No done randevour!";
        h3.style.marginLeft='auto';
        h3.style.marginRight='auto';
        div.append(h3);

        main.append(div);
        main.append(rowBtnContainer);
        return;
    }

    if (exams.length===0){
        div=document.createElement('div');
        div.setAttribute('class', 'msg');
        h3=document.createElement('h3');
        h3.innerHTML="No bloodtests!";
        h3.style.marginLeft='auto';
        h3.style.marginRight='auto';
        div.append(h3);

        main.append(div);
        main.append(rowBtnContainer);
        return;
    }

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
    div.style.width='95%';
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
            showTreatment(seeExams);
        });
        trtBtn.innerHTML='Treatment';
        tData.append(trtBtn);
        tRow.append(tData);
        tBody.append(tRow);

        //add the add treatment btn
        tData=document.createElement('td');
        trtBtn=document.createElement('button');
        trtBtn.setAttribute('id', 'treatment_' + exams[i].bloodtest_id); //the id helps to edit the correct treatment
        trtBtn.addEventListener('click', editTreatment);
        trtBtn.innerHTML='Edit';
        tData.append(trtBtn);
        tRow.append(tData);
        tBody.append(tRow);
    }

    tbl.append(tHead);
    tbl.append(tBody);
    div.append(tbl);

    main.append(div);
    main.append(rowBtnContainer);
}


function editTreatment(){
    var id=event.srcElement.id.split('_')[1];    //the bloodtest id
    var backBtn, form;
    var inputNames=["start-date", "end-date", "text"];
    var headers=["Start Date", "End Date", "Text"];
    var tbl, tr, th, td, sbmtBtn, input;

    form=document.createElement('form');
    backBtn=setBackBtn(seeExams);
    tbl=document.createElement('table');
    form.setAttribute('id', 'treatment-form_' + id);        //contains the bloodtest id
    form.setAttribute('onsubmit', 'postTreatment(this.id); return false;');
    sbmtBtn=document.createElement('button');
    sbmtBtn.setAttribute('type', 'submit');
    sbmtBtn.setAttribute('class', 'submit-btn');
    sbmtBtn.innerHTML="Add";

    for(var i=0; i<inputNames.length; i++){
        tr=document.createElement('tr');
        th=document.createElement('th');
        th.innerHTML=headers[i];
        tr.append(th);

        td=document.createElement('td');
        input=document.createElement('input');
        input.setAttribute('name', inputNames[i]);
        input.setAttribute('required', '');

        if(i===2){
            input.setAttribute('type', 'text');
        }else{
            input.setAttribute('type', 'date');
        }

        td.append(input);
        tr.append(td);
        tbl.append(tr);
    }

    tr=document.createElement('tr');
    th=document.createElement('th');
    th.setAttribute("colspan", "2");
    th.append(sbmtBtn);
    tr.append(th);
    tbl.append(tr);

    form.append(tbl);

    main.innerHTML='';
    rowBtnContainer.innerHTML='';
    main.append(form)
    rowBtnContainer.append(backBtn);
    main.append(rowBtnContainer);
}

function manageRandevouz(){
    var doc_id;
    var addBtn;     //btn to add randevou
    var backBtn;
    var div;
    var h3;

    const headers=["Date", "User Info", "Price", "Status", "Cancel", "Done"];
    const keys=["date_time", "user_info", "price", "status"];

    getCurrentUser();
    doc_id=loggedInUser.doctor_id;
    loggedInUser=null;
    getDocsRandevouz(doc_id);

    backBtn=setBackBtn(showDoctor);

    addBtn=document.createElement('button');
    addBtn.style.backgroundColor='var(--my_yellow)';
    addBtn.innerHTML="Add";
    addBtn.addEventListener('click', addRandevouz);

    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);
    rowBtnContainer.append(addBtn);

    main.innerHTML="";
    //no randevouz
    if(randevouz===null){
        div=document.createElement('div');
        div.setAttribute('class', 'msg');
        h3=document.createElement('h3');
        h3.innerHTML="No randevouz!";
        h3.style.marginLeft='auto';
        h3.style.marginRight='auto';
        div.append(h3);

        main.append(div);
        main.append(rowBtnContainer);
        return;
    }

    //display a table with the randevouz
    div=createRandevouzTable(headers, keys, "doc");
    main.append(div);
    main.append(rowBtnContainer);
}

function addRandevouz(){
    const inputNames=["date", "price"];
    var backBtn;
    var form=document.createElement('form');   //the form to enter the exams
    var sbmtBtn=document.createElement('button');    //the submit button
    var input;
    var tbl;
    var tRow;
    var tData;
    var tHeader;

    main.innerHTML="";

    backBtn=setBackBtn(manageRandevouz);
    rowBtnContainer.innerHTML="";
    rowBtnContainer.append(backBtn);

    tbl=document.createElement('table');
    form.setAttribute('id', 'randevouz-form');
    form.setAttribute('onsubmit', 'postRandevouz(); return false;');
    sbmtBtn.setAttribute("type", "submit");
    sbmtBtn.setAttribute("class", "submit-btn");
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
        }else{   //type price
            input.setAttribute('type', 'number');
            input.setAttribute('min', '1');
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

    main.append(form);

    main.append(rowBtnContainer);
}

