export class AppUser {
  userId: string = '';
  email: string = '';
  firstName: string = '';
  lastName: string = '';
  username: string = '';
  password?: string;
  roles?: string[];
  constructor() {}
}
export interface UserItem {
  icon: string;
  label: string;
}

export class LoginBody {
  username: string;
  password: string;
  constructor(username: string, password: string) {
    this.username = username;
    this.password = password;
  }
}

export class Tokens {
  refreshToken: string;
  accessToken: string;
  constructor(refreshToken: string, accessToken: string) {
    this.refreshToken = refreshToken;
    this.accessToken = accessToken;
  }
}
