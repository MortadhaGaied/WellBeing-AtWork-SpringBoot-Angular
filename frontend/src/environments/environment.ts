// This file can be replaced during build by using the `fileReplacements` array.
// `ng build` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

export const environment = {
  production: false,
  firebase: {
    apiKey: 'AIzaSyA1vRwVJ29Bh_HhQXiL-VPqFJlwD4nkl0U',
    authDomain: 'web-notifications-53dec.firebaseapp.com',
    databaseURL:
      'https://web-notifications-53dec-default-rtdb.europe-west1.firebasedatabase.app',
    projectId: 'web-notifications-53dec',
    storageBucket: 'web-notifications-53dec.appspot.com',
    messagingSenderId: '536047723421',
    appId: '1:536047723421:web:c73d667220b3955c038574',

    vapidKey:
      'BLQYIx6Ck9Q1L06GYYIe0ZlGrahOiDivygJE5oLwDRibfZIKJHpvWaD9sqytIDGHVRQFMRZowUQwS65UwQUCR74',
  },
  recaptcha: {
    siteKey: '6LfDwskfAAAAAB2zqHoxaUuvHzGHgtrSe2heGd-T',
    secretKey: '6LfDwskfAAAAAKNYPORn6LJHVxNmXe_ufWzgHsOk',
  },
};
/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
