import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { PublicProfileComponent } from '../settings/public-profile/public-profile.component';

const routes: Routes = [
  {
    path: 'public-profile',
    component: PublicProfileComponent,
  },
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ProductsRoutingModule {}
