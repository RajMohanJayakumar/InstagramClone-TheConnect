<!DOCTYPE html>

<head>
  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
  <title>The Connect</title>
  <meta name="google-signin-client_id"
    content="682848596913-k654bjpe8d5n9kdl5e9rkeha50hlq2rh.apps.googleusercontent.com">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="style.css">
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>

<body>
  <div id="login">
    <div id="header">
      <div id="logo">
        <h3 id="titleMain" style="font-size: 20px;">The Connect</h3>
      </div>
      <div id="search" class="form-group mx-sm-3 mb-2">
        <label class="sr-only">Search</label>
        <input type="text" class="form-control" placeholder="Search">

      </div>
      <div id="search-btn">
        <a class="btn btn-primary">Search</a>
      </div>
      <div id="addPost">
        <a class="btn btn-primary btn-sm" onclick="addPostBtn();">+ Add Post</a>
      </div>
    </div>
    <div id="dashboard">
      <div id="leftPanel">
        <div id="proSec">
          <div id="proPic" style="margin-top: 30px;"><img class="img" id="proPics" /></div>
          <center>
            <h4 style="font-size: 16px;margin-bottom: -22px;"><b>
                <div id="username">Username</div>
              </b></h4><br>
            <h4 style="font-size: 12px;">
              <div id="email">Email</div>
            </h4>
          </center>
        </div>
        <hr style="background: #b0cbe3;height: 1px;">
        <button class="leftPnlBtn">
          &nbsp;<h5 class="fa fa-flash" style="font-size:14px" onclick="feeds();">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Feeds</h5>
        </button>
        <button class="leftPnlBtn">
          <h5 class="fa fa-users" style="font-size:14px" onclick="friends();">&nbsp;&nbsp;&nbsp;&nbsp;Friends</h5>
        </button>
        <button class="leftPnlBtn">
          <h5 class="glyphicon glyphicon-send" style="font-size:14px" onclick="timeline();">&nbsp;Timeline</h5>
        </button>
        <button class="leftPnlBtn">
          <h5 class="fa fa-camera" style="font-size:14px" onclick="photos();">&nbsp;&nbsp;&nbsp;&nbsp;Photos</h5>
        </button>
        <button class="leftPnlBtn">
          <h5 class="glyphicon glyphicon-off" onclick="logout();" style="font-size:14px">&nbsp;Logout</h5>
        </button>
      </div>
      <div id="middlePanel">
        <div id="feedsPage" class="middlePanelFields">
          <div id="addNewPost">
            <div class="form-group purple-border">
              <label for="exampleFormControlTextarea4">Add Post</label>
              <textarea class="form-control" id="exampleFormControlTextarea4 feedTextArea" rows="3"
                placeholder="Write Something..."></textarea>
              <div id="uploadAndPostBtn" class="clearfix">
                <a id="postBtn" class="btn btn-primary mb-2 btn-xs" onclick="postFeed();">Post</a>
                <a id="uploadImage" class="btn btn-primary mb-2 btn-xs">+ Add photo</a>
              </div>
            </div>
          </div>

          <div id="feeds"></div>

          <div id="feedsPanel">
            <div id="feedInfo">
              <div id="feedProPic"></div>
              <h4 id="feedProName">Karthick Krishna</h4>
              <h4 class="feedText" style="font-size: 14px;">
                Pleasant Morning..
                Hot Coffee..
              </h4>
            </div>
            <figure id="feedPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <div id="feedPanel">
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Like <span class="badge badge-light">10</span>
              </button>
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Comment <span class="badge badge-light">4</span>
              </button>
            </div>
            <div id="cmtArea">
              <textarea class="form-control" id="exampleFormControlTextarea4" rows="3"
                placeholder="Write a comment..."></textarea>
              <a id="postBtn" class="btn btn-primary mb-2 btn-xs">Add Comment</a>
            </div>
          </div>

          <div id="feedsPanel">
            <div id="feedInfo">
              <div id="feedProPic"></div>
              <h4 id="feedProName">Mohan</h4>
              <h4 class="feedText" style="font-size: 14px;">
                Adventures...
              </h4>
            </div>
            <figure id="feedPic"><img class="img" src="images/feed1.jpg" /></figure>
            <div id="feedPanel">
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Like <span class="badge badge-light">10</span>
              </button>
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Comment <span class="badge badge-light">4</span>
              </button>
            </div>
            <div id="cmtArea">
              <textarea class="form-control" id="exampleFormControlTextarea4" rows="3"
                placeholder="Write a comment..."></textarea>
              <a id="postBtn" class="btn btn-primary mb-2 btn-xs">Add Comment</a>
            </div>
          </div>

          <div id="feedsPanel">
            <div id="feedInfo">
              <div id="feedProPic"></div>
              <h4 id="feedProName">Shiny Baskar</h4>
              <h4 class="feedText" style="font-size: 14px;">
                The Cold Sweet..
              </h4>
            </div>
            <figure id="feedPic"><img class="img" src="images/feed3.jpg" /></figure>
            <div id="feedPanel">
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Like <span class="badge badge-light">10</span>
              </button>
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Comment <span class="badge badge-light">4</span>
              </button>
            </div>
            <div id="cmtArea">
              <textarea class="form-control" id="exampleFormControlTextarea4" rows="3"
                placeholder="Write a comment..."></textarea>
              <a id="postBtn" class="btn btn-primary mb-2 btn-xs">Add Comment</a>
            </div>
          </div>

          <div id="feedsPanel">
            <div id="feedInfo">
              <div id="feedProPic"></div>
              <h4 id="feedProName">John Peterson</h4>
              <h4 class="feedText" style="font-size: 14px;">
                Relaxing!!
              </h4>
            </div>
            <figure id="feedPic"><img class="img" src="images/feed4.jpg" /></figure>
            <div id="feedPanel">
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Like <span class="badge badge-light">10</span>
              </button>
              <button type="button" class="feedBtn btn btn-primary btn-xs">
                Comment <span class="badge badge-light">4</span>
              </button>
            </div>
            <div id="cmtArea">
              <textarea class="form-control" id="exampleFormControlTextarea4" rows="3"
                placeholder="Write a comment..."></textarea>
              <a id="postBtn" class="btn btn-primary mb-2 btn-xs">Add Comment</a>
            </div>

            

          </div>
          <div id="endOfFeed">
            <hr>.</div>
        </div>

        <div class="gallery" class="middlePanelFields">
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
            <figure class="galleryPic"><img class="img" src="images/feed2.jpeg" /></figure>
        </div>
       
        <div id="clogout">

        </div>
        
      </div>
      
    </div>
    <div id="rightPanel">
        <center>
          <h5>&copy; AW Inc.</h5>
        </center>
      </div>
  </div>
  <script src="script.js"></script>
  <script>
    window.onload = function () {
      dashboard(updateUserDetails);
    };
  </script>
</body>

</html>