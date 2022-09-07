import { Component, HostListener, OnInit } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { UUID } from 'angular2-uuid';
import { AuthenticationService } from 'src/app/services/authentication/authentication.service';
import { AppUser } from 'src/app/model/user.model';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.scss'],
})
export class SignupComponent implements OnInit {
  newUserFormGroup!: FormGroup;
  innerWidth: number = window.innerWidth;
  roles: string[] = ['STUDENT', 'PROFESSOR'];
  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.newUserFormGroup = this.formBuilder.group({
      firstName: this.formBuilder.control(''),
      lastName: this.formBuilder.control(''),
      email: this.formBuilder.control(''),
      username: this.formBuilder.control(''),
      password: this.formBuilder.control(''),
      role: this.formBuilder.control(''),
    });
  }

  @HostListener('window:resize', ['$event'])
  onResize(event: any) {
    this.innerWidth = window.innerWidth;
  }

  async handleCreateAccount() {
    let id = UUID.UUID();
    let firstName = this.newUserFormGroup.value.firstName;
    let lastName = this.newUserFormGroup.value.lastName;
    let email = this.newUserFormGroup.value.email;
    let username = this.newUserFormGroup.value.username;
    let password = this.newUserFormGroup.value.password;
    let roles = [this.newUserFormGroup.value.role];
    let user: AppUser = {
      userId: id,
      email: email,
      firstName: firstName,
      lastName: lastName,
      username: username,
      password: password,
      roles: roles,
    };
    let newUser = await this.authenticationService.addUser(user);
    /*
    code that may use the new user data
    */
    this.router.navigateByUrl('/authentication/login');
  }

  // getClass(): string {
  //   return this.innerWidth < 480 ? 'form-container-md' : 'form-container';
  // }
}
