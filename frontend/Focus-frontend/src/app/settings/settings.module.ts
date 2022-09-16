import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ProductsRoutingModule } from '../products/products-routing.module';
import { PublicProfileComponent } from './public-profile/public-profile.component';
import { SettingsComponent } from './settings.component';

@NgModule({
  declarations: [PublicProfileComponent, SettingsComponent],
  imports: [ProductsRoutingModule, CommonModule],
  exports: [RouterModule],
})
export class SettingsModule {}
