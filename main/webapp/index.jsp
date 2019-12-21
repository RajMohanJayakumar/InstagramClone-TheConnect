<head>
  <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
  <title>The Connect</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
  <link rel="stylesheet" href="style.css">
  <script src="https://apis.google.com/js/platform.js" async defer></script>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>
<div class="header">
  <div class="fl-lf-al-cn">
    <h3 class="titleMain">The Connect</h3>
  </div>
  <div class="fl-lf-al-cn" class="form-group mx-sm-3 mb-2">
    <label class="sr-only">Search</label>
    <input type="text" class="form-control" placeholder="Search">

  </div>
  <div class="search-btn">
    <a class="btn btn-primary">Search</a>
  </div>
  <div class="fl-lf-al-cn addPost" class="sections">
    <a class="btn btn-primary btn-sm" href="#top" onclick="addPostBtn();">+ Add Post</a>
  </div>
</div>

<div class="dashboard clearfix" id="top">

  <div class="leftPanel">
    <div id="proSec">
      <div class="proPicAlign" style="margin-top: 30px;"><img class="img" id="proPic" /></div>
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
    <button class="leftPnlBtn" onclick="fetchWallFeeds();">
      &nbsp;<h5 class="fa fa-flash" style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Feeds</h5>
    </button>
    <button class="leftPnlBtn" onclick="fetchFriends();">
      <h5 class="fa fa-users" style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;Friends</h5>
    </button>
    <button class="leftPnlBtn" onclick="fetchTimeline();">
      <h5 class="glyphicon glyphicon-send" style="font-size:14px">&nbsp;Timeline</h5>
    </button>
    <button class="leftPnlBtn" onclick="fetchPhotos();">
      <h5 class="fa fa-camera" style="font-size:14px">&nbsp;&nbsp;&nbsp;&nbsp;Photos</h5>
    </button>
    <button class="leftPnlBtn" onclick="logout();">
      <h5 class="glyphicon glyphicon-off" style="font-size:14px">&nbsp;Logout</h5>
    </button>
  </div>

  <div class="middlePanel">
      <div id="addNewPost" class="postInput">
        <div class="form-group purple-border">
          <label for="exampleFormControlTextarea4">Add Post</label>
          <textarea class="form-control" id="feedTextArea" rows="3"
            placeholder="Write Something..."></textarea>
          <div id="uploadAndPostBtn" class="clearfix">
            <a id="postBtn" class="btn btn-primary mb-2 btn-xs" onclick="postFeed();">Post</a>
            <button id="addImage" class="btn btn-primary mb-2 btn-xs">+ Add Image</button>
            <input type="file" id="input-file">
            <input class="image-upload" type="file" id="attachFile" multiple="" style="cursor: pointer;" accept="image/*">
          </div>
        </div>
      </div>
      <div id="feedsPortion" class="sections"></div>

      <div id="friendsPortion" class="sections" style="display:none;">
        <div class="friendsPortion"><h2 style="margin-left: 65px;">Friends</h2></div>
          <div id="friendsContainer" class="friendsPortion"></div>
      </div>
    <div id="timelinePortion" class="sections" style="display:none;"></div>
    <div id="photosPortion" class="sections" style="display:none;"></div>


    <div id="editPortion" class="editPost">
      
    </div>
      <div class="endOfFeeds"></div>
    
  </div>

  <div class="rightPanel">
</div>

<script src="script.js"></script>
  <script>
    window.onload = function () {
      dashboard();
    };
  </script>
</body>