var url = "http://localhost:8080/";

    function logout(){
      call('session','delete')
      .then(res => {
        localStorage.clear();
        window.location.replace(url);
    });   
    }

  function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    signOut();

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
      call('currentuserdata','get')
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

function photos(){
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

function postFeed() {
  let feedText = document.getElementById('feedTextArea').value;
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
  })
 }

 function deleteFeed(feedId){
   call('feed?feedId='+feedId,'delete')
 }

 function feedCall(){
   call('feed?getFeeds=getAll','get')
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

 function feedIterate(feed,toPost){
   console.log(feed);
   document.getElementById(toPost).innerHTML = "";
   feed.forEach((e) => {
    if(localStorage.getItem(e.userId) == null){
      call(url+'userinfo?userId='+e.userId,'get')
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
                  <h4 style="font-size:16px; margin:0;font-weight: 700;">${userDetail.name}</h4>
                  <h4 style="font-size:13px; margin:0; opacity:0.5;font-weight: 700;">${new Date(e.timeStamp)}</h4>
                  </figcaption>
                  </figure>
              </div>
              <h4 class="feedText" style="font-size: 14px;">
                ${e.feedText}
              </h4>
            </div>`;
            
            var image = `<figure class="feedPic"><img class="img" src="${e.imageUrl}" /></figure>`;

            var feedBtns = `
            <div class="feedBtn">
              <a id="uploadImage" class="btn btn-primary mb-2 btn-xs" onclick=editFeed("${e.feedId}")>Edit</a>
            </div>
            </div>
            <div class="feedBtn">
              <a id="uploadImage" class="btn btn-primary mb-2 btn-xs" onclick=deleteFeed("${e.feedId}")>Delete</a>
            </div>
            </div>`;

            if(e.imageUrl != "null"){
              feedTemplate += image;
            }
            if(window.proUserId == e.userId){
            feedTemplate += feedBtns;
            }
            document.getElementById(toPost).innerHTML += feedTemplate;
  }

  function friendList(){
    friends();
    document.getElementById('friendsContainer').innerHTML = "";
    call('userinfo?userId=getAll','get')
    .then(res => {
        friendListIterator(res.data);
    })
  }

  function friendListIterator(friendList){
   friendList.forEach((friendList) => {
     if(window.proUserId != friendList.userId){
    if(localStorage.getItem(friendList.userId) == null){
      call(url+'userinfo?userId='+friendList.userId,'get')
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
                  <h4 style="font-size:14px; margin:0;font-weight: 700;">${friendList.name}</h4>
                  <h4 style="font-size:13px; margin:0; opacity:0.5;font-weight: 700;">${friendList.email}</h4>
                  </figcaption>
                  </figure>
              </div>`;

      document.getElementById('friendsContainer').innerHTML += friend;        
  }