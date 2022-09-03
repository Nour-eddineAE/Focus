export class AppUser {
  userId: string = '';
  email: string = '';
  firstName: string = '';
  lastName: string = '';
  username: string = '';
  password: string = '';
  roles: string[] = [];
  constructor() {}
}
export interface UserItem {
  icon: string;
  label: string;
}
