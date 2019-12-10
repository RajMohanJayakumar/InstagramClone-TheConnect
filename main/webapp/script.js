var url = "http://localhost:8080/";

// function onSignIn(googleUser,setSession) {
//     var profile = googleUser.getBasicProfile();
// //    profileInfo(profile.getName(),profile.getImageUrl(),profile.getEmail());
// // var proId = profile.getId();
//     console.log('ID: ' + profile.getId()); 
//     console.log('Name: ' + profile.getName());
//     console.log('Image URL: ' + profile.getImageUrl());
//     console.log('Email: ' + profile.getEmail());
//     setSession(profile.getId(),profile.getName(),profile.getImageUrl(),profile.getEmail());
//     function setSession(id,name,proPic,email,signOut){
//       // axios.post(url+'app', {
//       //     id:id,
//       //     name:name,
//       //     proPic:proPic,
//       //     email:email
//       // })

//       axios({
//         method: 'post',
//         url: url+'app',
//         headers: {}, 
//         data: {
//           id:id,
//           name:name,
//           proPic:proPic,
//           email:email
//         }
      
//         .then(res => {
//           console.log(res.data);
//           window.location.replace(url+'app?key='+res.data);
//         })
//       });
//         signOut();
//       function signOut() {
//         var auth2 = gapi.auth2.getAuthInstance()
//         auth2.signOut().then(function () {
//           console.log('User signed out.');
//         });
//       }
//       }
//     }



    function logout(){
        window.location.replace(url);
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

  

  // function login(){
  //   window.location.replace("http://localhost:8080/index.html");
  // }

  function onSignIn(googleUser) {
    var profile = googleUser.getBasicProfile();
    signOut();
    var id = profile.getId;
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