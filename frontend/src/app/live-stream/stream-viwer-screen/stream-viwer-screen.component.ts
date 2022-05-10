import { HttpClient } from '@angular/common/http';
import {
  AfterViewInit,
  Component,
  ElementRef,
  OnInit,
  ViewChild,
} from '@angular/core';

@Component({
  selector: 'app-stream-viwer-screen',
  templateUrl: './stream-viwer-screen.component.html',
  styleUrls: ['./stream-viwer-screen.component.css'],
})
export class StreamViwerScreenComponent implements OnInit {
  constructor(private http: HttpClient) {}

  @ViewChild('video')
  public video: ElementRef;
  stream: MediaStream;
  ngOnInit(): void {
    /*setTimeout(() => {
      this.startStream();
    }, 1000);*/
    //this.startStream();
  }
  async startStream() {
    await this.init();
    console.log(this.stream);
  }

  async init() {
    const peer = this.createPeer();
    peer.addTransceiver('video', { direction: 'recvonly' });
  }

  createPeer(): RTCPeerConnection {
    const peer = new RTCPeerConnection({
      iceServers: [
        {
          urls: 'stun:stun.stunprotocol.org',
        },
      ],
    });
    peer.ontrack = this.handleTrackEvent;
    console.log(peer);
    peer.onnegotiationneeded = async () =>
      await this.handleNegotiationNeededEvent(peer);

    return peer;
  }

  async handleNegotiationNeededEvent(peer: RTCPeerConnection) {
    const offer = await peer.createOffer();
    await peer.setLocalDescription(offer);
    const payload = {
      sdp: peer.localDescription,
    };

    //const { data } = await axios.post('/consumer', payload);
    this.http
      .post('http://localhost:5000/consumer', payload)
      .subscribe((data: any) => {
        const desc = new RTCSessionDescription(data.sdp);
        peer.setRemoteDescription(desc).catch((e) => console.log(e));
      });
  }

  handleTrackEvent = (e: any) => {
    //console.log(e.streams[0]);
    this.stream = e.streams[0];
    this.video.nativeElement.srcObject = e.streams[0];
    //this.video.nativeElement.play();
  };
}
