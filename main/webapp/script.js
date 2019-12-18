var url = "http://localhost:8080/";

    function logout(){
      call('session','delete')
      .then(res => {
        localStorage.clear();
        window.location.replace(url);
    });   
    }

    let sections = document.querySelectorAll('.sections');
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
      feedCall();
      // feeds();
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
      document.getElementById("addNewPost").style.display = 'none';
      document.getElementById('gallery').style.display = 'none';
      document.getElementsById('friendPage').style.display = 'none';
      document.getElementById('feeds').style.display = 'block';
  feedCall();
}

// function iterateFeeds(feed){
//   feed.forEach((e) => { 
//     var feed1 = `
// <div id="feedsPanel">
// <div id="feedInfo">
// <figure id="feedProPic"><img class="img" src="${e.proImgUrl}" /></figure>
//   <h4 id="feedProName">${e.name}</h4>
//     <h4 class="feedText" style="font-size: 14px;">
//           ${e.feedText}               
//     </h4>
// </div>
// <figure id="feedPic"><img class="img" src="${e.feedImgUrl}" /></figure>
// </div>
// `;
// document.getElementById("feeds").appendChild(feed1);
// })
// }
// }

function friends(){
    document.getElementById("addNewPost").style.display = 'none';
      document.getElementById('gallery').style.display = 'none';
      document.getElementById('feeds').style.display = 'none';
      document.getElementsById('timelineFeeds').style.display = 'none';
      document.getElementsById('friendPage').style.display = 'block';
}

function feeds(){
  window.location.reload();
  document.getElementById("addNewPost").style.display = 'none';
  document.getElementById('gallery').style.display = 'none';
  document.getElementsById('friendPage').style.display = 'none';
  document.getElementsById('timelineFeeds').style.display = 'none';
  document.getElementById('feeds').style.display = 'block';
}

function timeline(){
  timelineFeed();
  document.getElementById("addNewPost").style.display = 'none';
  document.getElementById('gallery').style.display = 'none';
  document.getElementById('feeds').style.display = 'none';
  document.getElementsById('friendPage').style.display = 'none';
  document.getElementsById('timelineFeeds').style.display = 'block';
}

function photos(){
  document.getElementById("addNewPost").style.display = 'none';
  document.getElementById('feeds').style.display = 'none';
  document.getElementsById('friendPage').style.display = 'none';
  document.getElementsById('timelineFeeds').style.display = 'none';
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

 function deleteFeed(feedId){
   call('feed?feedId='+feedId,'delete')
 }

 function feedCall(){
   call(url+"feed?getFeeds=getAll",'get')
   .then(res => {
      feedIterate(res.data,'feeds');
   })
 }

 function timelineFeed(){
  call('feed?getFeeds=getUserFeeds&fetch='+window.proUserId,'get')
  .then(res => {
     feedIterate(res.data,'timelineFeeds');
  })
}

 function feedIterate(feed,toPost){
   console.log(feed);
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
   <div class="feedsPanel" class="sections">
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
            </div>
            <figure class="feedPic"><img class="img" src="${e.imageUrl}" /></figure>`;

            var deleteFeedBtn = `
            <div class="feedBtn">
              <a id="uploadImage" class="btn btn-primary mb-2 btn-xs" onclick=editFeed("${e.feedId}")>Edit</a>
            </div>
            </div>
            <div class="feedBtn">
              <a id="uploadImage" class="btn btn-primary mb-2 btn-xs" onclick=deleteFeed("${e.feedId}")>Delete</a>
            </div>
            </div>`;
            if(window.proUserId == e.userId){
            feedTemplate = feedTemplate+deleteFeedBtn;
            }
            document.getElementById(toPost).innerHTML += feedTemplate;
   
  }