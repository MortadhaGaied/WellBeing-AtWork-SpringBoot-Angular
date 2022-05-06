import { MatDialog } from '@angular/material/dialog'
import { UpdateEventComponent } from '../update-event/update-event.component';

  import { Event } from '../../Models/Forum/Event/Event';
  import { Component,EventEmitter,  Output , ViewChild } from '@angular/core';
  import { EventService } from '../event.service';
  import {
      MbscCalendarEvent,
      MbscDatepickerOptions,
      MbscEventcalendarOptions,
      MbscPopup,
      MbscPopupOptions,
      Notifications,
      setOptions
      , localeEnGB
  } from '@mobiscroll/angular';
  
  setOptions({
      locale: localeEnGB,
      theme: 'ios',
      themeVariant: 'light'
  });
  
  @Component({
    selector: 'app-calendar',
    templateUrl: './calendar.component.html',
    styleUrls: ['./calendar.component.scss'],
    providers:[Notifications]
  })
  export class CalendarComponent {
    @Output () eventEmmit= new EventEmitter();
      constructor(private notify: Notifications,private _service:EventService,private matDialog:MatDialog,) {}
      @ViewChild('popup', { static: false })
      popup!: MbscPopup;
      @ViewChild('colorPicker', { static: false })
      colorPicker: any;
      listEvent : Event[];
      popupEventTitle: string | undefined;
      popupEventDescription = '';
      popupEventAllDay = true;
      popupEventDates: any;
      popupEventStatus = 'busy';
      calendarSelectedDate: any = new Date();
      switchLabel: any = 'All-day';
      tempColor = '';
      selectedColor = '';
      colorAnchor: HTMLElement | undefined;
      colors = ['#ffeb3c', '#ff9900', '#f44437', '#ea1e63', '#9c26b0', '#3f51b5', '', '#009788', '#4baf4f', '#7e5d4e'];
      event:Event=new Event();
      myEvents: MbscCalendarEvent[] = [];
      tempEvent!: MbscCalendarEvent;
      ngOnInit(): void {

        this._service.getAllEvent().subscribe(res=>
            {
                console.log(res);
                this.listEvent=res
                for(var i=0;i<res.length;i++){
                    this.myEvents[i]={
                        id: res[i].idEvent,
                        start: res[i].startDate,
                        end: res[i].endDate,
                        title: res[i].eventName,
                        description: 'test',
                        allDay: true,
                        free: true,
                        color: '#009788'
                    }
                }
                this.myEvents = [...this.myEvents];
            });
      
      }
      calendarOptions: MbscEventcalendarOptions = {
          
          dragToCreate: true,
          dragToMove: true,
          dragToResize: true,
          view: {
              schedule: { type: 'week' }
          },
          onEventClick: (args) => {
              console.log("***************************\n"+typeof(args.event.id));
              if((typeof(args.event.id)=='number')){
                this._service.sendEventData(args.event.id);
              }
              
                this.matDialog.open(UpdateEventComponent);
          },
          
          onEventDeleted: (args) => {
              setTimeout(() => {
                if((typeof(args.event.id)=='number')){
                    this._service.deleteEventById(args.event.id)
                    .subscribe(()=>this._service.getAllEvent().subscribe(res=>{this.listEvent=res}));
                }
              });
          },
          onEventUpdated: (args) => {
              // here you can update the event in your storage as well, after drag & drop or resize
              // ...
          }
      };
      popupHeaderText!: string;
      popupAnchor: HTMLElement | undefined;
      
      popupEditButtons = ['cancel', {
          handler: () => {
              this.saveEvent();
          },
          keyCode: 'enter',
          text: 'Save',
          cssClass: 'mbsc-popup-button-primary'
      }];
      popupButtons: any = [];
      popupOptions: MbscPopupOptions = {
          display: 'bottom',
          contentPadding: false,
          fullScreen: true,
          onClose: () => {
              if (!this.isEdit) {
                  // refresh the list, if add popup was canceled, to remove the temporary event
                  this.myEvents = [...this.myEvents];
              }
          },
          responsive: {
              medium: {
                  display: 'anchored',
                  width: 400,
                  fullScreen: false,
                  touchUi: false
              }
          }
      };
      datePickerControls = ['date'];
      datePickerResponsive: any = {
          medium: {
              controls: ['calendar'],
              touchUi: false
          }
      };
      datetimePickerControls = ['datetime'];
      datetimePickerResponsive = {
          medium: {
              controls: ['calendar', 'time'],
              touchUi: false
          }
      };
      datePickerOptions: MbscDatepickerOptions = {
          select: 'range',
          showRangeLabels: false,
          touchUi: true
      };
      isEdit = false;
      colorOptions: MbscPopupOptions = {
          display: 'bottom',
          contentPadding: false,
          showArrow: false,
          showOverlay: false,
          buttons: [
              'cancel',
              {
                  text: 'Set',
                  keyCode: 'enter',
                  handler: (ev) => {
                      this.selectedColor = this.tempColor;
                      this.colorPicker.close();
                  },
                  cssClass: 'mbsc-popup-button-primary'
              }
          ],
          responsive: {
              medium: {
                  display: 'anchored',
                  buttons: [],
              }
          }
      };
      loadPopupForm(event: MbscCalendarEvent): void {
          this.popupEventTitle = event.title;
          this.popupEventDescription = event.description;
          this.popupEventDates = [event.start, event.end];
          this.popupEventAllDay = event.allDay || false;
          this.popupEventStatus = event.status || 'busy';
          this.selectedColor = event.color || '';
      }
      saveEvent(): void {
          this.tempEvent.title = this.popupEventTitle;
          this.tempEvent.description = this.popupEventDescription;
          this.tempEvent.start = this.popupEventDates[0];
          this.tempEvent.end = this.popupEventDates[1];
          this.tempEvent.allDay = this.popupEventAllDay;
          this.tempEvent.status = this.popupEventStatus;
          this.tempEvent.color = this.selectedColor;
          if (this.isEdit) {
              // update the event in the list
              this.myEvents = [...this.myEvents];
              // here you can update the event in your storage as well
              // ...
          } else {
              // add the new event to the list
              this.myEvents = [...this.myEvents, this.tempEvent];
              // here you can add the event to your storage as well
              // ...
          }
          // navigate the calendar
          this.calendarSelectedDate = this.popupEventDates[0];
          // close the popup
          this.popup.close();
      }
      deleteEvent(event: MbscCalendarEvent): void {
          this.myEvents = this.myEvents.filter(item => item.id !== event.id);
          this.notify.snackbar({
              button: {
                  action: () => {
                      this.myEvents = [...this.myEvents, event];
                  },
                  text: 'Undo'
              },
              message: 'Event deleted'
          });
          // here you can delete the event from your storage as well
          // ...
      }
      onDeleteClick(): void {
          this.deleteEvent(this.tempEvent);
          this.popup.close();
      }
  
      selectColor(color: string): void {
          this.tempColor = color;
      }
  
      openColorPicker(ev: any): void {
          this.selectColor(this.selectedColor || '');
          this.colorAnchor = ev.currentTarget;
          this.colorPicker.open();
      }
  
      changeColor(ev: any): void {
          const color = ev.currentTarget.getAttribute('data-value');
          this.selectColor(color);
  
          if (!this.colorPicker.s.buttons.length) {
              this.selectedColor = color;
              this.colorPicker.close();
          }
      }
  }
  