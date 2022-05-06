import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-stream-screen',
  templateUrl: './stream-screen.component.html',
  styleUrls: ['./stream-screen.component.css'],
})
export class StreamScreenComponent implements OnInit {
  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.init();
  }

  muted: boolean = false;
  data: any;
  stream: MediaStream | undefined;
  peer: RTCPeerConnection;

  toggleMuted() {
    this.muted = !this.muted;
  }

  async startStream() {
    await this.init();
  }

  async init() {
    console.log('stream starting ...');
    const stream = await navigator.mediaDevices.getUserMedia({
      video: true,
      audio: true,
    });
    if (stream) {
      console.log('stream is here boyz ');
    }

    this.stream = stream;
    console.log('steram here ' + stream);
    this.peer = this.createPeer();
    stream.getTracks().forEach((track) => this.peer.addTrack(track, stream));
  }

  close() {
    this.stream = undefined;
  }

  createPeer() {
    const peer = new RTCPeerConnection({
      iceServers: [
        {
          urls: 'stun:stun.stunprotocol.org',
        },
      ],
    });
    peer.onnegotiationneeded = () => this.handleNegotiationNeededEvent(peer);

    return peer;
  }

  async handleNegotiationNeededEvent(peer: RTCPeerConnection) {
    const offer = await peer.createOffer();
    await peer.setLocalDescription(offer);
    const payload = {
      sdp: peer.localDescription,
    };

    this.http
      .post('http://localhost:5000/broadcast', payload)
      .subscribe((data: any) => {
        const desc = new RTCSessionDescription(data.sdp);
        peer.setRemoteDescription(desc).catch((e) => console.log(e));
      });
  }

  shareScreen() {
    this.close();
    navigator.mediaDevices.getDisplayMedia({ video: true }).then((stream) => {
      const screenTrack = stream.getTracks()[0];
      this.stream = stream;
      this.peer = this.createPeer();
      stream.getTracks().forEach((track) => this.peer.addTrack(track, stream));
    });
  }
}
