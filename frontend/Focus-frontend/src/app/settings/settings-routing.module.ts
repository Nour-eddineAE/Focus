import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicProfileComponent } from './public-profile/public-profile.component';

const routes: Routes = [
  {
    path: 'public-profile',
    component: PublicProfileComponent,
  },
];

@NgModule({
  imports: [RouterModule.forRoot(routes, { onSameUrlNavigation: 'reload' })],
  exports: [RouterModule],
})
export class SettingsRoutingModule {}
