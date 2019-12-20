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
        console.log('User signed out.');
      });
    }

    var data;
    
    function dashboard(){
      call('user?user=currentuser','get')
    .then(res => {
        data = res.data;
        if(data != null){
      window.proUserId = data.userId;
      console.log(window.proUserId);
      document.getElementById('username').innerHTML = data.name;
      document.getElementById('email').innerHTML = data.email;
      document.getElementById('proPic').setAttribute('src',data.proPicUrl);
      feeds(feedCall);
      }
    });
    }

    function updateUserDetails(proName,proEmail,proImgUrl){
      console.log(proName);
      console.log(proEmail);
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

function call(url,method,payload){
  return axios({
    method: method,
    url: url,
    headers: {}, 
    data: payload
})
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
      console.log(e.imageUrl)
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
   console.log(window.proUserId);
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
   console.log(feed);
   document.getElementById(toPost).innerHTML = "";
   feed.forEach((e) => {
    if(localStorage.getItem(e.userId) == null){
      console.log("fri"+e.userId);
      call('user?user='+e.userId,'get')
      .then(res => {
        console.log(res.data);
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
     console.log(userDetail);
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
      console.log("fri"+friendList.userId)
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
    console.log(divProperties);
    document.getElementById('editPortion').style.display = 'block';
  }

function closeEdit(){
  document.getElementById('editPortion').style.display = 'none';
}