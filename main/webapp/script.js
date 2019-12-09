function onSignIn(googleUser,setSession) {
    var profile = googleUser.getBasicProfile();
//    profileInfo(profile.getName(),profile.getImageUrl(),profile.getEmail());
// var proId = profile.getId();
    console.log('ID: ' + profile.getId()); 
    console.log('Name: ' + profile.getName());
    console.log('Image URL: ' + profile.getImageUrl());
    console.log('Email: ' + profile.getEmail());
    setSession(profile.getId(),profile.getName(),profile.getImageUrl(),profile.getEmail());
    function setSession(id,name,proPic,email,signOut){
        // axios.get('http://localhost:8080/app?id='+id+'&name='+name+'&proPic='+proPic+'&email='+email, {})
        // .then(res => {
          window.location.replace('http://localhost:8080/app?id='+id+'&name='+name+'&proPic='+proPic+'&email='+email)
        // signOut();
        // function signOut() {
            var auth2 = gapi.auth2.getAuthInstance()
            auth2.signOut().then(function () {
              console.log('User signed out.');
            // });
          // }
    })
    
      }
    }

    function logout(){
        window.location.replace("http://localhost:8080");
    }

    function updateUserDetails(username,email,imgUrl){
      document.getElementById('username').innerHTML = "Hi, Raj Mohan";
      document.getElementById('email').innerHTML = "rajmohan2695@gmail.com";
    }

  

//function profileInfo(name,imgUrl,email){
//	document.getElementById('proName').innerText = name;
//	let str = ``;
//	document.getElementById('update').innerText = name;
//	document.getElementById('proEmail').innerText = email;
//}

  

  function login(){
    window.location.replace("http://localhost:8080/index.html");
  }