function onSignIn(googleUser) {
  var profile = googleUser.getBasicProfile();
  signOut();
  window.proUserId = profile.getId();
  payload = [{
   userId: profile.getId(),
   name: profile.getName(),
   proPicUrl: profile.getImageUrl(),
   email: profile.getEmail()
  }];
 
  call('session', 'post', payload)
   .then(res => {
    window.location.reload();
   });
 }
 
 function signOut() {
  var auth2 = gapi.auth2.getAuthInstance()
  auth2.signOut().then(function() {});
 }
 
 function traditionalSignin() {
  var username = document.getElementById('username').value;
  var password = document.getElementById('password').value;
  payLoad = [{
   username: username,
   password: password
  }];
  call('traditionalLogin', 'post', payLoad)
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
     console.log("1")
     fetchWallFeeds("");
    }
   });
 }
 
 function updateUserDetails(proName, proEmail, proImgUrl) {
  document.getElementById('username').innerHTML = proName;
  document.getElementById('email').innerHTML = proEmail;
 }
 
 //Populate the wall with cursor
 // function fetchWallFeeds(cursor) {
 //   hideSections();
 //   document.getElementById('feedsPortion').innerHTML = "";
 //   document.getElementById('feedsPortion').style.display = 'block';
 //   window.location.href = "#top";
 //     call('feed?getFeeds=getAll1&cursor='+cursor, 'get')
 //     .then(res => {
 //       feedIterate(res.data.feeds);
 //       if(res.data.cursor != "")
 //         fetchWallFeeds(res.data.cursor)
 //     });
 // }
 
 //Populate the wall
 function fetchWallFeeds() {
  hideSections();
  document.getElementById('feedsPortion').style.display = 'block';
  reachTop();
  call('feed?getFeeds=getAll', 'get')
   .then(res => {
    feedIterate(res.data, 'feedsPortion');
   })
 }
 
 //populate the friends list portion
 function fetchFriends() {
  hideSections();
  document.getElementById('friendsPortion').style.display = 'block';
  call('user?user=getAll', 'get')
   .then(res => {
    friendListIterator(res.data);
   })
 }
 
 //function to fetch different timelines using parameters
 function fetchTimeline(userId) {
  hideSections();
  document.getElementById('timelinePortion').innerHTML = "";
  document.getElementById('timelinePortion').style.display = 'block';
  if (typeof userId == "undefined") {
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
  reachTop();
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
  if (photos.length == 0) {
   var noFeeds = `<center><h4 style="opacity: 0.5; font-weight: 700;">- no photos to show -</h4></center>`;
   document.getElementById('photosPortion').innerHTML = noFeeds;
   return;
  }
  photos.forEach((e) => {
   if (e.imageUrl != "null") {
    let photoPortion = `<figure class="galleryPic"><img class="img" src="${e.imageUrl}" /></figure>`;
    document.getElementById('photosPortion').innerHTML += photoPortion;
   }
  })
 }
 
 // Add Post
 function postFeed(feedId, method) {
  payload = [{
   feedId: feedId,
   feedText: window.feedText,
   imageUrl: window.imageUrl,
   timeStamp: new Date().getTime()
  }]
 
  window.image = "null";
  window.feedText = "null";
  window.feedImage = "null";
  window.imageUrl = "null";
 
  call('feed', method, payload)
   .then(res => {
    document.getElementById('feedTextArea').value = "";
    document.getElementById("addNewPost").style.display = "none";
    document.getElementById("editPortion").style.display = "none";
    document.getElementById('attachFile').value = "";
    fetchWallFeeds("");
 
   })
 }
 
 //deleting the the current feed
 function deleteFeed(feedId, currentDiv) {
  call('feed?feedId=' + feedId, 'delete')
   .then(res => {
    currentDiv.parentNode.parentNode.parentNode.remove();
   })
 }
 
 // // Feed Iterate for cursor
 // function feedIterate(feed) {
 // if(feed.length == 0){
 //   var noFeeds = `<center><h4 style="opacity: 0.5; font-weight: 700;">- no feeds to display -</h4></center>`;
 //   document.getElementById('timelinePortion').innerHTML = noFeeds;
 //   return;
 // }
 // feed.forEach((e) => {
 //   if (localStorage.getItem(e.userId) == null) {
 //   call('user?user=' + e.userId, 'get')
 //     .then(res => {
 //     localStorage.setItem(e.userId, JSON.stringify(res.data));
 //     postFeedFunction(e, 'timelinePortion');
 //     })
 //   } else {
 //   postFeedFunction(e, 'timelinePortion');
 //   }
 // })
 // }
 
 function feedIterate(feed, toPost) {
  document.getElementById(toPost).innerHTML = "";
  if (feed.length == 0) {
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
 
  var image = `<figure class="feedPic" onclick="showPhoto(this);"><img class="img" src="${e.imageUrl}" /></figure>`;
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

 function showPhoto(feed){
  //  feed = feed.getElementById('feedBtns').remove;
  feedText = feed.parentElement.getElementsByTagName('h4')[2].innerText;
  feedHead = feed.parentElement.getElementsByClassName('feedPro')[0].innerHTML;
  feedImg = feed.getElementsByClassName('img')[0].src;
  console.log(feedImg);
  // console.log(feedHead);
   document.getElementById('gallery').style.display = 'block';
   document.getElementById('imageToShow').innerHTML = "";
   document.getElementById('userDetailInImage').innerHTML = feedHead;
   document.getElementById('feedTextInImage').innerText = feedText;
   document.getElementById('imageToShow').innerHTML = `<img class="imgPreview" src="${feedImg}" />`
  //  console.log(feed.getElementById('feedInfo').innerHTML)
 }
 
 function friendListIterator(friendList) {
  document.getElementById('friendsContainer').innerHTML = "";
  document.getElementById('friendsContainerSearch').innerHTML = "";
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
  document.getElementById('friendsContainerSearch').innerHTML += friend;
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
         <a id="postBtn" class="btn btn-primary mb-2 btn-xs" onclick="updateFeedController('${feedId}');">update</a>
         <input class="image-upload" type="file" id="attachupdateFile" style="cursor: pointer;" accept="image/*">
         <a id="closeEdit" class="btn btn-primary mb-2 btn-xs" onclick="closeEdit();">Close</a>
       </div>
     </div>
   </div>
 `;
  document.getElementById('editPortion').innerHTML = feedUpdate;
  document.getElementById('editPortion').style.display = 'block';
 }

 function closeImage(){
  document.getElementById('gallery').style.display = 'none';
 }
 
 function closeEdit() {
  document.getElementById('editPortion').style.display = 'none';
 }
 
 //To get the image upload URL
 function imageUpload(feedId, method) {
  loadImage(true);
  if (typeof window.feedImage == "undefined") {
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
    imageUrl = res.data.data.uploadUrl;
    uploadImageFile(imageUrl, feedId, method);
   })
 }
 
 //imageUpload function
 function uploadImageFile(uploadUrl, feedId, method) {
 
  headers2 = {
   "Content-Type": "multipart/form-data",
   "Access-Control-Allow-Origin": "*"
  }
 
  let data = new FormData();
 
  data.append('file', window.feedImage);
 
  call(uploadUrl, "post", data, headers2)
   .then(res => {
    loadImage(false);
    window.imageUrl = res.data.data.files[0].img_serve_url;
    postFeed(feedId, method);
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
  document.getElementById('friendsPortionSearch').style.display = 'none';
  document.getElementById('feedsPortion').style.display = 'none';
  document.getElementById('photosPortion').style.display = 'none';
  document.getElementById("addNewPost").style.display = 'none';
  document.getElementById('gallery').style.display = 'none';
 }
 
 function search() {
  document.getElementById("friendsPortionSearch").style.display = 'block';
  let friends = document.querySelector('#friendsPortionSearch');
  friends.style.height="100vh";
  document.querySelector('body').style.overflow='hidden';
  var input, filter, h4, name, a, i, txtValue;
  input = document.getElementById("myInput");
  filter = input.value.toUpperCase();
  friendList = document.querySelectorAll(".proDetail");
  for (i = 0; i < friendList.length; i++) {
   h4 = friendList[i].getElementsByTagName("h4");
   name = h4[0];
   txtValue = name.textContent || name.innerText;
   if (document.getElementById('myInput').value.length < 1) {
    document.getElementById("friendsPortionSearch").style.display = 'none';
    friends.style.height="0";
    document.querySelector('body').style.overflow='auto';
   }
   if (txtValue.toUpperCase().indexOf(filter) > -1) {
    friendList[i].style.display = "block";
    
   } else {
    friendList[i].style.display = "none";
   }
  }
 }
 
 function postFeedController() {
  window.feedText = document.getElementById('feedTextArea').value;
  let feedTextTrim = feedText.trim();
  if (typeof document.querySelector('#attachFile').files[0] == "undefined" && feedTextTrim == "") {
   return;
  } else if (typeof document.querySelector('#attachFile').files[0] != "undefined") {
   window.feedImage = document.querySelector('#attachFile').files[0];
   imageUpload("", "post");
  } else {
   window.feedImage = "null";
   postFeed("", "post");
  }
 }
 
 function updateFeedController(feedId) {
  window.feedText = document.getElementById('feedUpdateTextArea').value;
  let feedTextTrim = feedText.trim();
  if (typeof document.querySelector('#attachupdateFile').files[0] != "undefined") {
   window.feedImage = document.querySelector('#attachupdateFile').files[0];
   imageUpload(feedId, "put");
  } else {
   window.feedImage = "null";
   postFeed(feedId, "put");
  }
 }
 
 function reachTop() {
  document.body.scrollTop = 0;
  document.getElementById('myInput').value="";
  document.body.style.overflow='auto';
 }
 
 function loadImage(show) {
  if (show) {
   document.getElementById("loadingSymbol").style.display = "block";
  } else {
   document.getElementById('loadingSymbol').style.display = "none";
  }
 }
 
 function feedcursors() {
  var cursor = document.getElementById("cursor").value;
  call('feed?getFeeds=getAll1&cursor=' + cursor, get)
   .then(res => {
    document.getElementById.innerText = res.data.cursor;
    feedIterate(res.data);
   })
 }