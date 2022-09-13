import { Component, OnInit } from '@angular/core';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { UserService } from 'src/app/services/users/user.service';

@Component({
  selector: 'app-public-profile',
  templateUrl: './public-profile.component.html',
  styleUrls: ['./public-profile.component.scss'],
})
export class PublicProfileComponent implements OnInit {
  userProfilePictureURL =
    this.userService.host +
    this.userService.getProfilePicURL +
    this.userService.authenticatedUserUsername;
  constructor(private userService: UserService) {}

  ngOnInit(): void {}
}
