function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  signOut();
  window.proUserId = profile.getId();
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

function traditionalSignin(){
  var username = document.getElementById('username').value;
  var password = document.getElementById('password').value;
  payLoad = [{
    username:username,
    password:password
  }];
  call('traditionalLogin','post',payLoad)
  .then(res => {
    window.location.reload();
});
}

function logout() {
call('session', 'delete')
  .then(res => {
  localStorage.clear();
  window.location.reload();
  });
}

//The initial load
function dashboard() {
call('user?user=currentuser', 'get')
  .then(res => {
  data = res.data;
  if (data != null) {
    fetchFriends();
    window.proUserId = data.userId;
    document.getElementById('username').innerHTML = data.name;
    document.getElementById('email').innerHTML = data.email;
    document.getElementById('proPic').setAttribute('src', data.proPicUrl);
    fetchWallFeeds();
  }
  });
}

function updateUserDetails(proName, proEmail, proImgUrl) {
document.getElementById('username').innerHTML = proName;
document.getElementById('email').innerHTML = proEmail;
}

//Populate the wall
function fetchWallFeeds() {
  hideSections();
  document.getElementById('feedsPortion').style.display = 'block';
  window.location.href = "#top";
  call('feed?getFeeds=getAll', 'get')
    .then(res => {
      feedIterate(res.data, 'feedsPortion');
    })
}
//populate the friends list portion
function fetchFriends() {
  hideSections();
  document.getElementById('friendsPortion').style.display = 'block';
  window.location.href = "#top";
  call('user?user=getAll', 'get')
  .then(res => {
    friendListIterator(res.data);
  })
}

//function to fetch different timelines using parameters
function fetchTimeline(userId) {
  hideSections();
  document.getElementById('timelinePortion').style.display = 'block';
  console.log("friend"+window.proUserId);
  console.log(userId)
  if(typeof userId == "undefined"){
    console.log(window.proUserId);
    userId = window.proUserId;
  }
  call('feed?getFeeds=getUserFeeds&fetch=' + userId, 'get')
  .then(res => {
    feedIterate(res.data, 'timelinePortion');
  })
}

//Populate the photos portion
function fetchPhotos() {
  hideSections();
  document.getElementById('photosPortion').style.display = 'block';
  window.location.href = "#top";
  call('photos', 'get')
  .then(res => {
    photoIterate(res.data);
  })
}

//toggling the Add button template portion
function addPostBtn() {
  var x = document.getElementById("addNewPost");
  if (x.style.display == "block") {
    x.style.display = "none";
  } else {
    x.style.display = "block";
  }
}


function photoIterate(photos) {
  document.getElementById('photosPortion').innerHTML = "";
  photos.forEach((e) => {
    if (e.imageUrl != "null") {
      let photoPortion = `<figure class="galleryPic"><img class="img" src="${e.imageUrl}" /></figure>`;
      document.getElementById('photosPortion').innerHTML += photoPortion;
    }
  })
}

// Add Post
function postFeed() {
  let feedText = document.getElementById('feedTextArea').value;
  payload = [{
    feedText: feedText,
    imageUrl: window.image,
    timeStamp: new Date().getTime()
  }]

  window.Image = "null";

  call('feed', 'post', payload)
  .then(res => {
    document.getElementById('feedTextArea').value = "";
    document.getElementById("addNewPost").style.display = "none";
    document.getElementById('attachFile').value = "";
    fetchWallFeeds();
  })
}

//deleting the the current feed
function deleteFeed(feedId, currentDiv) {
  call('feed?feedId=' + feedId, 'delete')
  .then(res => {
    currentDiv.parentNode.parentNode.parentNode.remove();
  })
}

function feedIterate(feed, toPost) {
document.getElementById(toPost).innerHTML = "";
if(feed.length == 0){
  var noFeeds = `<center><h4 style="opacity: 0.5; font-weight: 700;">- no feeds to display -</h4></center>`;
  document.getElementById(toPost).innerHTML = noFeeds;
  return;
}
feed.forEach((e) => {
  if (localStorage.getItem(e.userId) == null) {
  call('user?user=' + e.userId, 'get')
    .then(res => {
    localStorage.setItem(e.userId, JSON.stringify(res.data));
    postFeedFunction(e, toPost);
    })
  } else {
  postFeedFunction(e, toPost);
  }
})
}

function postFeedFunction(e, toPost) {
var userDetail = JSON.parse(localStorage.getItem(e.userId));
var feedTemplate = `
<div class="feedsPanel">
        <div id="feedInfo">
          <div class="feedPro">
            <figure style="margin:0 0 14px">
              <img src="${userDetail.proPicUrl}" class="feedProPic" alt="">
              <figcaption style="display: inline-block;
              vertical-align:middle;">
              <h4 style="font-size:14px; margin:0;font-weight: 700; cursor: pointer" onclick="fetchTimeline('${userDetail.userId}')">${userDetail.name}</h4>
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

if (e.imageUrl != "null") {
  feedTemplate += image;
}
if (e.isEdit == "true") {
  edit = `<div style="opacity : 0.5;">Edited</div>`
}
if (window.proUserId == e.userId) {
  feedTemplate += feedController;
}
document.getElementById(toPost).innerHTML += feedTemplate;
}

function friendListIterator(friendList) {
document.getElementById('friendsContainer').innerHTML = "";
friendList.forEach((friendList) => {
  if (window.proUserId != friendList.userId) {
  if (localStorage.getItem(friendList.userId) == "null") {
    call('user?user=' + friendList.userId, 'get')
    .then(res => {
      localStorage.setItem(friendList.userId, JSON.stringify(res.data));
      friendListFunction(friendList);
    })
  } else {
    friendListFunction(friendList);
  }
  }
})
}

function friendListFunction(friendList) {
  var friend = `
    <div class="feedPro proDetail">
              <figure style="margin:0 0 14px">
                <img src="${friendList.proPicUrl}" class="feedProPic" alt="">
                <figcaption style="display: inline-block;
                vertical-align:middle;">
                <h4 style="font-size:14px; margin:0;font-weight: 700; cursor: pointer" onclick="fetchTimeline('${friendList.userId}')">${friendList.name}</h4>
                <h4 style="font-size:13px; margin:0; opacity:0.5;font-weight: 700;">${friendList.email}</h4>
                </figcaption>
                </figure>
            </div>`;
  document.getElementById('friendsContainer').innerHTML += friend;
}

function editPost(feedId, divProperties) {
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

function closeEdit() {
  document.getElementById('editPortion').style.display = 'none';
}

function updateFeed(feedId) {
var feedUpdateText = document.getElementById('feedUpdateTextArea').value;
payLoad = [{
  feedId: feedId,
  // imageUrl:  ,
  feedText: feedUpdateText
}];
call('feed', 'put', payLoad)
  .then(res => {
  window.location.reload();
  closeEdit();
  })
}

//To get the image upload URL
function imageUpload() {
  window.image = "null";
  var imageFile = document.querySelector('#attachFile').files[0];
  console.log(image);
  if(typeof imageFile == "undefined"){
    return;
  }
headers = {
  "Authorization": "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJSUzI1NiIsImtpZCI6ImQxNzJhY2MxMDJjMGRhYWQzNThiZmM5ZDYwZWEyMWJhOWVjY2I2ZWUifQ.eyJpc3MiOiJodHRwczovL2FueXdoZXJlLnN0YWdpbmcuYW55d2hlcmVhdXRoLmNvbSIsImlhdCI6MTU3Njg1NjgzNiwicHJval9pZCI6ImFueXdoZXJld29ya3MiLCJ0eXBlIjoidXNlciIsInN1YiI6IjdiZTE0MGIzLWNiOTEtNDVmZC1hOWY0LTU5NTVlMWZmOGIxNCIsImV4cCI6MTU3NzQ2MTYzNiwianRpIjoiMGFkMDZlSmJzYmM2T2tCMCJ9.X9nz0eXdrEiv7C5pIl18mWnm1EEujdnsjRf-1EHNq1bW4YZjg77PHubXc34iJkmM3By8Mg-_jeomQff_L-xXp_n3XWGy0mX6PFMwhcY3_ME1qeuW63aybKr_f86AfXSg_fCKkgVY3Ay6MVTnvxMfroc1VCP2J3m6Xv1rNJAjrEIvBGKC_1eGxbo94-iYEKj0iP9UxEPRJYKjwfDC_zd2xJGkZgd65FP1HlsueqOztigL9Ee3yPSLwppQj6IabgzUi7deFpyIMb3UE7N9XskqSKL5l39DcPVeP42x8ZGpTL1kZPgnAS4JzN4ZV3JgsoDZGXJFDVCaaiyoaX7KpMmT3A"
}
payLoad = {
  "access_type": "public"
};
call('https://api-dot-staging-fullspectrum.appspot.com/api/v1/file/upload/url', 'post', payLoad, headers)
  .then(res => {
  console.log(res);
  window.imageUrl = uploadImageFile(imageFile,res.data.data.uploadUrl);
  })


}

//imageUpload function
function uploadImageFile(imageFile,uploadUrl) {
console.log("Upload URL - " + uploadUrl);

headers2 = {
  "Content-Type": "multipart/form-data",
  "Access-Control-Allow-Origin": "*"
}

let data = new FormData();

data.append('file', imageFile);

call(uploadUrl, "post", data, headers2)
  .then(res => {
  window.image = res.data.data.files[0].img_serve_url;
  postFeed();
  })
}

//Axios api call
function call(url, method, payload, headers) {
return axios({
  method: method,
  url: url,
  headers: headers,
  data: payload
})
}

//To hide all the sections
function hideSections() {
document.getElementById('timelinePortion').style.display = 'none';
document.getElementById('friendsPortion').style.display = 'none';
document.getElementById('feedsPortion').style.display = 'none';
document.getElementById('photosPortion').style.display = 'none';
document.getElementById("addNewPost").style.display = 'none';
// document.getElementById("search").style.display = 'none';
}

function search(){
  hideSections();
    document.getElementById("friendsPortion").style.display = 'block';
  
    var input, filter, h4, name, a, i, txtValue;

    input = document.getElementById("myInput");

    filter = input.value.toUpperCase();

    friendList = document.getElementsByClassName("proDetail");
    

    for (i = 0; i < friendList.length; i++) {
      h4 = friendList[i].getElementsByTagName("h4");
        name = h4[0];
        txtValue = name.textContent || name.innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
          friendList[i].style.display = "";
        } else {
          friendList[i].style.display = "none";
        }
    }
}

// function feedCursor() {
//   hideSections();
//   document.getElementById('feedsPortion').style.display = 'block';
//   window.location.href = "#top";
//   call('friends', 'delete')
//     .then(res => {
//       feedIterate(res.data, 'feedsPortion');
//     })
// }

$( document ).on( "pagecreate", function() {
      $( ".photopopup" ).on({
          popupbeforeposition: function() {
              var maxHeight = $( window ).height() - 60 + "px";
              $( ".photopopup img" ).css( "max-height", maxHeight );
          }
      });
  });


  function postFeedController(){
    let feedText = document.getElementById('feedTextArea').value;
    let feedTextTrim = feedText.trim();
    if(typeof document.querySelector('#attachFile').files[0] == "undefined" && feedTextTrim == ""){
      return;
    }else if(typeof document.querySelector('#attachFile').files[0] != "undefined"){
      imageUpload();
    }else{
      postFeed();
    }
      
  }