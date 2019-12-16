var url = "http://localhost:8080/";

    function logout(){
      axios.delete(url+'app?key='+window.location.href)
      .then(res => {
        // document.cookie = 'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
        window.location.replace(url);
    });
        
    }

var proId;
var proName;
var proImg;
var proEmail;

  function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    signOut();
    proId = profile.getId();
    proName = profile.getName();
    proImg = profile.getImageUrl();
    proEmail = profile.getEmail();
    console.log('ID: ' + profile.getId()); 
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());

    axios({
      method: 'post',
      url: url+'redirect',
      headers: {}, 
      data: [{
        userId:profile.getId(),
        name:profile.getName(),
        proPicUrl:profile.getImageUrl(),
        email:profile.getEmail()
      }]
  })
  .then(res => {
      // var key = res.data;
      // window.location.href=url+'app?key='+key;
      window.location.href = url+'index';
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
      axios.get(url+'userdata')
    .then(res => {
        data = res.data;
        if(data != null){
        document.getElementById('username').innerHTML = data.name;
      document.getElementById('email').innerHTML = data.email;
      document.getElementById('proPics').setAttribute('src',data.imgUrl);
      }
    });
  //  updateUserDetails(data.name,data.email,data.imgUrl);
    }

    function updateUserDetails(proName,proEmail,proImgUrl){
      console.log(proName);
      console.log(proEmail);
      document.getElementById('username').innerHTML = proName;
      document.getElementById('email').innerHTML = proEmail;
    }    

    //function profileInfo(name,imgUrl,email){
//	document.getElementById('proName').innerText = name;
//	let str = ``;
//	document.getElementById('update').innerText = name;
//	document.getElementById('proEmail').innerText = email;
//}


function feeds(){

  axios.get(url+'feeds')
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
  document.getElementsByClassName('middlePanelFields').style.display = none;
}

function feeds(){
  window.location.reload();
}

function timeline(){
  document.getElementsByClassName('middlePanelFields').innerHTML = "";
}

function photos(){
  document.getElementById('feedsPage').innerHTML = "";
  // document.getElementsByClassName('middlePanelFields').innerHTML = "";
  document.getElementById('gallery').style.display=block;
}

// for (var key in validation_messages) {
//   // skip loop if the property is from prototype
//   if (!validation_messages.hasOwnProperty(key)) continue;

//   var obj = validation_messages[key];
//   for (var prop in obj) {
//       // skip loop if the property is from prototype
//       if (!obj.hasOwnProperty(prop)) continue;

//       // your code
//       alert(prop + " = " + obj[prop]);
//   }
// }