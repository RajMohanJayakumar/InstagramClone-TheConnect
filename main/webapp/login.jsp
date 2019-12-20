<!DOCTYPE html>

<head>
  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
  <title>The Connect</title>
  <meta name="google-signin-client_id" content="682848596913-k654bjpe8d5n9kdl5e9rkeha50hlq2rh.apps.googleusercontent.com">
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="style.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <style>
      .g-signin2{
        margin-left: 46%;
      }
  </style>
</head>

<body>
  <div class="logout">
    <div class="header">
      <div class="fl-lf-al-cn">
        <h3 class="titleMain">The Connect</h3>
      </div>
    </div>
    <div id="logoutPanel">
        <div id="mid40">
                <h3><center>Welcome to The Connect</center></h3>
                <div class="form-group">
                        <div class="gsign" style="margin-left: -57px;">
                          <div class="g-signin2" data-onsuccess="onSignIn"></div>
                        </div>
                        <label>Username</label>
                        <input class="form-control" type="text" id="name" placeholder="Enter email">
                    </div>
                    <div class="form-group">
                        <label>Password</label>
                        <input class="form-control" type="password" id="name" placeholder="Enter password">
                    </div>
                    <button id="add" type="submit" class="btn btn-sm btn-primary" style="float:left;">Sign In</button>
                    <button id="add" type="submit" class="btn btn-sm btn-primary" style="float:left;margin-left: 10px;">Sign Up</button>
        </div>
            </div>
    </div>
    <script src="script.js"></script>
    </body>
    </html>