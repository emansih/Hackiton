var auth = new firebaseui.auth.AuthUI(firebase.auth());
auth.start('#auth-container', {
    signInOptions: [
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
        firebase.auth.GithubAuthProvider.PROVIDER_ID,
        firebase.auth.EmailAuthProvider.PROVIDER_ID,
        firebase.auth.PhoneAuthProvider.PROVIDER_ID
    ],
});

var uiConfig = {
    callbacks: {
        signInSuccessWithAuthResult: function(authResult, redirectUrl) {
            return true;
        },
        uiShown: function() {
        }
    },
    signInFlow: 'popup',
    signInSuccessUrl: 'dashboard',
    signInOptions: [
        firebase.auth.GoogleAuthProvider.PROVIDER_ID,
        firebase.auth.GithubAuthProvider.PROVIDER_ID,
        firebase.auth.EmailAuthProvider.PROVIDER_ID,
        firebase.auth.PhoneAuthProvider.PROVIDER_ID
    ]
};
auth.start('#auth-container', uiConfig);
firebase.auth().onAuthStateChanged((user) => {
    if (user) {
        setCookie('userId', user.uid)
    }
});

function setCookie(name,value) {
    var expires = "";
    var date = new Date();
    date.setTime(date.getTime() + (24*60*60*1000));
    expires = "; expires=" + date.toUTCString();
    document.cookie = name + "=" + (value || "")  + expires + "; path=/";
}