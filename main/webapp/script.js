var url = "http://localhost:8080/";

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
      call(url+'userdata','get')
    .then(res => {
        data = res.data;
        if(data != null){
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
<div id="feedPanel">
  <button type="button" class="feedBtn btn btn-primary btn-xs">
    Like <span class="badge badge-light">${e.likesCount}</span>
  </button>
  <button type="button" class="feedBtn btn btn-primary btn-xs">
    Comment <span class="badge badge-light">${e.commentsCount}</span>
  </button>
</div>
<div id="cmtArea">
  <textarea class="form-control" id="exampleFormControlTextarea4" rows="3" placeholder="Write a comment..."></textarea>
  <a id="postBtn" class="btn btn-primary mb-2 btn-xs">Add Comment</a>
</div>
</div>
`;
document.getElementById("feeds").appendChild(feed1);
})
}
}

function friends(){
  document.getElementsById('feedsPage').style.display = 'none';
}

function feeds(){
  window.location.reload();
}

function timeline(){
  document.getElementsById('feedsPage').style.display = 'none';
}

function photos(){
  document.getElementById('feedsPage').style.display = 'none';
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

  call(url+'feed','post',payload);
 }