var url = "http://localhost:8080/";

    function logout(){
      axios.delete(url+'app?key='+window.location.href)
      .then(res => {
        document.cookie = 'JSESSIONID=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
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
      url: url+'app',
      headers: {}, 
      data: {
        id:profile.getId(),
        name:profile.getName(),
        imgUrl:profile.getImageUrl(),
        email:profile.getEmail()
      }
  })
  .then(res => {
      var key = res.data;
      window.location.href=url+'app?key='+key;
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
      axios.put(url+'app')
    .then(res => {
        data = res.data;
        document.getElementById('username').innerHTML = data.name;
      document.getElementById('email').innerHTML = data.email;
      document.getElementById('proPics').setAttribute('src',data.imgUrl);
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