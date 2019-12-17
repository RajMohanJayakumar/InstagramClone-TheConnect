var url = "http://localhost:8080/";
var proUserId;
    function logout(){
      call(url+'session','delete')
      .then(res => {
        window.location.replace(url);
    });   
    }

    let sections = document.querySelectorAll('.sections')
    function hideSections(sections) {
    sections.forEach(function(userItem) {
    userItem.style.display = 'none';
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

    call(url+'session','post',payload)
    .then(res => {
      window.location.href = url;
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
      call(url+'currentuserdata','get')
    .then(res => {
        data = res.data;
        if(data != null){
          proUserId = data.userId;
        document.getElementById('username').innerHTML = data.name;
      document.getElementById('email').innerHTML = data.email;
      document.getElementById('proPics').setAttribute('src',data.proPicUrl);
      document.getElementById("addNewPost").style.display = 'none';
      }
    });
    }

    function updateUserDetails(proName,proEmail,proImgUrl){
      console.log(proName);
      console.log(proEmail);
      document.getElementById('username').innerHTML = proName;
      document.getElementById('email').innerHTML = proEmail;
    }    

function feeds(){

  call(url+'feeds','get')
  .then(res =>{
    iterateFeeds(res.data);
});

function iterateFeeds(feed){
  feed.forEach((e) => { 
    var feed1 = `
<div id="feedsPanel">
<div id="feedInfo">
<figure id="feedProPic"><img class="img" src="${e.proImgUrl}" /></figure>
  <h4 id="feedProName">${e.name}</h4>
    <h4 class="feedText" style="font-size: 14px;">
          ${e.feedText}               
    </h4>
</div>
<figure id="feedPic"><img class="img" src="${e.feedImgUrl}" /></figure>
</div>
`;
document.getElementById("feeds").appendChild(feed1);
})
}
}

function friends(){
  hideSections();
  document.getElementsById('feedsfriendPage').style.display = 'none';
}

function feeds(){
  window.location.reload();
  hideSections();
}

function timeline(){
  hideSections();
  document.getElementsById('feedsPage').style.display = 'none';
}

function photos(){
  hideSections();
  document.getElementById('gallery').style.display = 'block';
}

function addPostBtn(){
  var x = document.getElementById("addNewPost");
  if (x.style.display === "none") {
    x.style.display = "block";
  } else {
    x.style.display = "none";
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
  console.log(feedText);
  let timeStamp = new Date().getTime();
  console.log(timeStamp);
  let imageUrl = "image.jpg";
  payload = [{
    feedText : feedText,
    timeStamp : timeStamp,
    imageUrl : imageUrl
  }] 

  call(url+'feed','post',payload)
  .then(res =>{
    document.getElementById('feedTextArea').value = "";
    document.getElementById("addNewPost").style.display = "none";
  })
 }

 function feedCall(){
   call(url+"feed?getFeeds=getAll",'get')
   .then(res => {
      feedIterate(res.data);
   })
 }

 function feedIterate(feed){
   console.log(feed);
   feed.forEach((e) => {
    if(localStorage.getItem(e.userId) == null){
      call(url+'userinfo?userId='+e.userId,'get')
      .then(res => {
        console.log(res.data);
           localStorage.setItem(e.userId,JSON.stringify(res.data));
           postFeedFunction(e);
      })
    }
    else{
      postFeedFunction(e);
    }
  })
}

function postFeedFunction(e){
     var userDetail = JSON.parse(localStorage.getItem(e.userId));
     console.log(userDetail);
   var feedTemplate = `
   <div id="feedsPanel" class="sections">
            <div id="feedInfo">
              <div class="feedPro">
                <figure style="margin:0 0 14px">
                  <img src="${userDetail.proPicUrl}" style="width:45px; height:45px; border-radius: 50%; vertical-align: middle;" alt="">
                  <figcaption style="display: inline-block;
                  vertical-align:middle;">
                  <h4 style="font-size:16px; margin:0;font-weight: 700;">${userDetail.name}</h4>
                  <h4 style="font-size:16px; margin:0; opacity:0.5;font-weight: 700;">${e.timeStamp}</h4>
                  </figcaption>
                  </figure>
              </div>
              <h4 class="feedText" style="font-size: 14px;">
                ${e.feedText}
              </h4>
            </div>
            <figure id="feedPic"><img class="img" src="${e.imageUrl}" /></figure>
            <div class="deleteBtn">
              <a id="uploadImage" class="btn btn-primary mb-2 btn-xs">Delete</a>
            </div>
            </div>
   `;
   document.getElementById('feeds').innerHTML += feedTemplate;
   
  }

 function iter(){
 for(var i = 0 ; i<10 ; i++){
   console.log("11");
   feedIterate();
 }
}

console.log(proUserId);