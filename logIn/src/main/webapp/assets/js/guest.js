

function showGuest(){
    document.getElementById('login-container').style.display='none';
    document.getElementById('logout-btn').style.display='block';
    main.innerHTML="";

    var findDocs=document.createElement("button");
    var register=document.createElement("button");
    findDocs.innerHTML="Find Doctors";
    register.innerHTML="Register";

    findDocs.addEventListener('click', guestDocs);

    columnBtnContainer.innerHTML="";
    columnBtnContainer.append(findDocs);
    columnBtnContainer.append(register);
    main.append(columnBtnContainer);
}

function guestDocs(){
    var i;
    var length;
    var backBtn;

    backBtn=setBackBtn(showGuest);

    getDocs();

    main.innerHTML="";
    docsContainer.innerHTML="";
    btnList=[];

    length=docs.length;

    //create a button for every doc

    for(i=0; i<length; i++){
        if(docs[i].certified==0)
            continue;

        var btn=document.createElement('button');
        btn.setAttribute('id', "doc_" + i);     //the id helps to find the correct array and the correct user to print
        btn.innerHTML=docs[i].username;
        btn.addEventListener('click', guestDocInfo);
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

function guestDocInfo(){
    var index=event.srcElement.id.split('_')[1];      //the id of the button is the index of the doctor in the array
    var div=document.createElement('div');
    var p=document.createElement('p');
    var backBtn;

    main.innerHTML="";

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
    backBtn=setBackBtn(guestDocs);

    rowBtnContainer.append(backBtn);

    main.append(div);
    main.append(rowBtnContainer);
}