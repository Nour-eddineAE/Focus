import { INavbarData } from '../model/sidenav.model';

export const navbarData: INavbarData[] = [
  {
    routeLink: 'dashboard',
    icon: 'fa fa-home',
    label: 'Dashboard',
  },
  {
    routeLink: 'products',
    icon: 'fa fa-box-open',
    label: 'Products',
    items: [
      {
        routeLink: 'products/level1.1',
        icon: 'fa fa-circle',
        label: 'level1.1',
        items: [
          {
            routeLink: 'products/level2.1',
            icon: 'fa fa-circle',
            label: 'level2.1',
            items: [
              {
                routeLink: 'products/level3.1',
                icon: 'fa fa-circle',
                label: 'level3.1',
              },
              {
                routeLink: 'products/level3.2',
                icon: 'fa fa-circle',
                label: 'level3.2',
              },
            ],
          },
          {
            routeLink: 'products/level2.2',
            icon: 'fa fa-circle',
            label: 'level2.2',
          },
        ],
      },
      {
        routeLink: 'products/level1.2',
        icon: 'fa fa-circle',
        label: 'level1.2',
      },
    ],
  },
  {
    routeLink: 'statistics',
    icon: 'fa fa-chart-bar',
    label: 'Statistics',
  },
  {
    routeLink: 'coupens',
    icon: 'fa fa-tags',
    label: 'Coupens',
    items: [
      {
        routeLink: 'coupens/list',
        icon: 'fa fa-circle',
        label: 'List Coupens',
      },
      {
        routeLink: 'coupens/create',
        icon: 'fa fa-circle',
        label: 'Create Coupens',
      },
    ],
  },
  {
    routeLink: 'pages',
    icon: 'fa fa-file',
    label: 'Pages',
  },
  {
    routeLink: 'media',
    icon: 'fa fa-camera',
    label: 'Media',
  },
  {
    routeLink: 'settings',
    icon: 'fa fa-cog',
    label: 'Settings',
    expanded: false,
    items: [
      {
        routeLink: 'settings/public-profile',
        icon: 'fa fa-user',
        label: 'Profile',
      },
      {
        routeLink: 'settings/customize',
        icon: 'fa fa-circle',
        label: 'Customize',
      },
    ],
  },
];
