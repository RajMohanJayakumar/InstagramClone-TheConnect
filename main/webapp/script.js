    function logout(){
      call('session','delete')
      .then(res => {
        localStorage.clear();
        window.location.reload();
    });   
    }

  function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    signOut();
    window.proUserId = profile.userId;
    payload = [{
      userId:profile.getId(),
      name:profile.getName(),
      proPicUrl:profile.getImageUrl(),
      email:profile.getEmail()
    }];

    call('session','post',payload)
    .then(res => {
      window.location.reload();
  });
}

    function signOut() {
      var auth2 = gapi.auth2.getAuthInstance()
      auth2.signOut().then(function () {
      });
    }

    var data;
    
    function dashboard(){
      call('user?user=currentuser','get')
    .then(res => {
        data = res.data;
        if(data != null){
      window.proUserId = data.userId;
      document.getElementById('username').innerHTML = data.name;
      document.getElementById('email').innerHTML = data.email;
      document.getElementById('proPic').setAttribute('src',data.proPicUrl);
      feeds(feedCall);
      }
    });
    }

    function updateUserDetails(proName,proEmail,proImgUrl){
      document.getElementById('username').innerHTML = proName;
      document.getElementById('email').innerHTML = proEmail;
    }    

function feeds(callback){
  document.getElementById('photosPortion').style.display = 'none'; 
  document.getElementById('timelinePortion').style.display = 'none';
  document.getElementById('friendsPortion').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'block';
  callback();
}

function friends(){
  document.getElementById('photosPortion').style.display = 'none'; 
  document.getElementById('timelinePortion').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'none';
  document.getElementById('friendsPortion').style.display = 'block';
}

function timeline(){
  timelineFeed();
  document.getElementById('photosPortion').style.display = 'none'; 
  document.getElementById('friendsPortion').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'none';
  document.getElementById('timelinePortion').style.display = 'block';
}

function friendsTimeline(userId){
  
  document.getElementById('photosPortion').style.display = 'none'; 
  document.getElementById('friendsPortion').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'none';
  document.getElementById('timelinePortion').style.display = 'block';
  friendTimeline(userId);
}

function photos(){
  photosCall()
  document.getElementById('timelinePortion').style.display = 'none';
  document.getElementById('friendsPortion').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'none';
  document.getElementById('photosPortion').style.display = 'block'; 
  document.getElementById('photosPortion').innerHTML = "GALLERY"
}

function addPostBtn(){
  var x = document.getElementById("addNewPost");
  if (x.style.display == "block") {
    x.style.display = "none";
  } else {
    x.style.display = "block";
  }
}



function photosCall(){
  call('photos','get')
  .then(res => {
     photoIterate(res.data);
  })
  
}

function photoIterate(photos){
  document.getElementById('photosPortion').innerHTML = "";
  photos.forEach((e) => {
    if(e.imageUrl != "null"){
    let photoPortion = `<figure class="galleryPic"><img class="img" src="${e.imageUrl}" /></figure>`;
    document.getElementById('photosPortion').innerHTML += photoPortion;
    }
  })
}


function postFeed() {
  let feedText = document.getElementById('feedTextArea').value;
  let feedTextTrim = feedText.trim();
  if(feedTextTrim == ""){
    return;
  }
  let timeStamp = new Date().getTime();
  payload = [{
    feedText : feedText,
    imageUrl : "https://www.outsideonline.com/sites/default/files/styles/img_1400x800/public/2019/10/10/man-reads-book-fall_h.jpg?itok=FX-pxiaq",
    timeStamp : timeStamp
  }] 

  call('feed','post',payload)
  .then(res =>{
    document.getElementById('feedTextArea').value = "";
    document.getElementById("addNewPost").style.display = "none";
    window.location.reload();
  })
 }

 function deleteFeed(feedId,currentDiv){
   call('feed?feedId='+feedId,'delete')
   .then(res => {
     currentDiv.parentNode.parentNode.parentNode.remove();
   })
 }

 function feedCall(){
   call('feed?getFeeds=getAll&timeStamp'+new Date().getTime(),'get')
   .then(res => {
      feedIterate(res.data,'feedsPortion');
   })
 }

 function timelineFeed(){
  call('feed?getFeeds=getUserFeeds&fetch='+window.proUserId,'get')
  .then(res => {
     feedIterate(res.data,'timelinePortion');
  })
}

function friendTimeline(userId){
  call('feed?getFeeds=getUserFeeds&fetch='+userId,'get')
  .then(res => {
     feedIterate(res.data,'timelinePortion');
  })
}

 function feedIterate(feed,toPost){
   document.getElementById(toPost).innerHTML = "";
   feed.forEach((e) => {
    if(localStorage.getItem(e.userId) == null){
      call('user?user='+e.userId,'get')
      .then(res => {
           localStorage.setItem(e.userId,JSON.stringify(res.data));
           postFeedFunction(e,toPost);
      })
    }
    else{
      postFeedFunction(e,toPost);
    }
  })
}

function postFeedFunction(e,toPost){
     var userDetail = JSON.parse(localStorage.getItem(e.userId));
   var feedTemplate = `
   <div class="feedsPanel">
            <div id="feedInfo">
              <div class="feedPro">
                <figure style="margin:0 0 14px">
                  <img src="${userDetail.proPicUrl}" class="feedProPic" alt="">
                  <figcaption style="display: inline-block;
                  vertical-align:middle;">
                  <h4 style="font-size:14px; margin:0;font-weight: 700; cursor: pointer" onclick="friendsTimeline('${userDetail.userId}')">${userDetail.name}</h4>
                  <h4 style="font-size:13px; margin:0; opacity:0.5;font-weight: 700;">${new Date(e.timeStamp)}</h4>
                  </figcaption>
                  </figure>
              </div>
              <h4 class="feedText" style="font-size: 14px;">
                ${e.feedText}
              </h4>
            </div>`;
            
            var image = `<figure class="feedPic"><img class="img" src="${e.imageUrl}" /></figure>`;
            var isEdited = `<div class="isEdited></div>`;
            var feedController = `
            <div class="feedBtns">
            <div class="feedBtn">
              <a id="editButton" class="btn btn-primary mb-2 btn-xs" style="font-size: 13px;" onclick=editPost("${e.feedId}",this)>Edit</a>
            </div>
            <div class="feedBtn">
              <a id="deleteButton" class="btn btn-primary mb-2 btn-xs" style="font-size: 13px;" onclick=deleteFeed("${e.feedId}",this)>Delete</a>
            </div>
            </div>
            `;

            if(e.imageUrl != "null"){
              feedTemplate += image;
            }
            if(e.isEdit == "true"){
              edit = `<div style="opacity : 0.5;">Edited</div>`
            }
            if(window.proUserId == e.userId){
            feedTemplate += feedController;
            }
            document.getElementById(toPost).innerHTML += feedTemplate;
  }

  function friendList(){
    friends();
    document.getElementById('friendsContainer').innerHTML = "";
    call('user?user=getAll','get')
    .then(res => {
        friendListIterator(res.data);
    })
  }

  function friendListIterator(friendList){
   friendList.forEach((friendList) => {
     if(window.proUserId != friendList.userId){
    if(localStorage.getItem(friendList.userId) == "null"){
      call('user?user='+friendList.userId,'get')
      .then(res => {
           localStorage.setItem(friendList.userId,JSON.stringify(res.data));
           friendListFunction(friendList);
      })
    }
    else{
      friendListFunction(friendList);
    }
  }
  })
}

function friendListFunction(friendList){
      var friend = `
      <div class="feedPro">
                <figure style="margin:0 0 14px">
                  <img src="${friendList.proPicUrl}" class="feedProPic" alt="">
                  <figcaption style="display: inline-block;
                  vertical-align:middle;">
                  <h4 style="font-size:14px; margin:0;font-weight: 700; cursor: pointer" onclick="friendsTimeline('${friendList.userId}')">${friendList.name}</h4>
                   <h4 style="font-size:13px; margin:0; opacity:0.5;font-weight: 700;">${friendList.email}</h4>
                  </figcaption>
                  </figure>
              </div>`;

      document.getElementById('friendsContainer').innerHTML += friend;        
  }



  function editPost(feedId,divProperties){
    var feedText = divProperties.parentElement.parentElement.parentElement.getElementsByClassName("feedText")[0].innerText;
    var feedUpdate = `
    <div id="editPost">
        <div class="form-group purple-border">
          <label for="exampleFormControlTextarea4">Edit Post</label>
          <textarea class="form-control" id="feedUpdateTextArea" rows="3"
            >${feedText}</textarea>
          <div id="uploadAndPostBtn" class="clearfix">
            <a id="postBtn" class="btn btn-primary mb-2 btn-xs" onclick="updateFeed('${feedId}');">update</a>
            <a id="updateImage" class="btn btn-primary mb-2 btn-xs">+ update Image</a>
            <a id="closeEdit" class="btn btn-primary mb-2 btn-xs" onclick="closeEdit();">Close</a>
          </div>
        </div>
      </div>
    `;
    document.getElementById('editPortion').innerHTML = feedUpdate;
    document.getElementById('editPortion').style.display = 'block';
  }

  function updateFeed(feedId){
      var feedUpdateText = document.getElementById('feedUpdateTextArea').value;
      payLoad = [{
        feedId:feedId,
        // imageUrl:  ,
        feedText:feedUpdateText
      }];
      call('feed','put',payLoad)
      .then(res => {
        window.location.reload();
        closeEdit();
      })
  }

function closeEdit(){
  document.getElementById('editPortion').style.display = 'none';
}

function getUploadUrl(){
  headers = {"Authorization" : "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6ImQxNzJhY2MxMDJjMGRhYWQzNThiZmM5ZDYwZWEyMWJhOWVjY2I2ZWUifQ.eyJpc3MiOiJodHRwczovL2FueXdoZXJlLnN0YWdpbmcuYW55d2hlcmVhdXRoLmNvbSIsImlhdCI6MTU3Njg1NjgzNiwicHJval9pZCI6ImFueXdoZXJld29ya3MiLCJ0eXBlIjoidXNlciIsInN1YiI6IjdiZTE0MGIzLWNiOTEtNDVmZC1hOWY0LTU5NTVlMWZmOGIxNCIsImV4cCI6MTU3NzQ2MTYzNiwianRpIjoiMGFkMDZlSmJzYmM2T2tCMCJ9.X9nz0eXdrEiv7C5pIl18mWnm1EEujdnsjRf-1EHNq1bW4YZjg77PHubXc34iJkmM3By8Mg-_jeomQff_L-xXp_n3XWGy0mX6PFMwhcY3_ME1qeuW63aybKr_f86AfXSg_fCKkgVY3Ay6MVTnvxMfroc1VCP2J3m6Xv1rNJAjrEIvBGKC_1eGxbo94-iYEKj0iP9UxEPRJYKjwfDC_zd2xJGkZgd65FP1HlsueqOztigL9Ee3yPSLwppQj6IabgzUi7deFpyIMb3UE7N9XskqSKL5l39DcPVeP42x8ZGpTL1kZPgnAS4JzN4ZV3JgsoDZGXJFDVCaaiyoaX7KpMmT3A"}
  payLoad = {"access_type": "public"};
  call('https://api-dot-staging-fullspectrum.appspot.com/api/v1/file/upload/url','post',payLoad,headers)
  .then(res => {
    console.log(res);
    imageUpload(res.data.data.uploadUrl);
  })
}

function imageUpload(uploadUrl){
   var image = document.querySelector('#attachFile').files[0];

   headers2 = {
    "Content-Type": "multipart/form-data",
    "Access-Control-Allow-Origin": "*"
   }
   payLoad = {
     "data":image
   }
    call(uploadUrl,"post",payLoad,headers2)
      .then(res => {
        console.log(res.data)
          var json = res;
          var url = json.data.data.files.img_serve_url;
          console.log(url);
      })
}

function call(url,method,payload,headers){
  return axios({
    method: method,
    url: url,
    headers: headers, 
    data: payload
})
}