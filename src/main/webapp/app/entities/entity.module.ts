import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'recipe',
        loadChildren: () => import('./recipe/recipe.module').then(m => m.NooMeRecipeModule),
      },
      {
        path: 'ingredient',
        loadChildren: () => import('./ingredient/ingredient.module').then(m => m.NooMeIngredientModule),
      },
      {
        path: 'food-item',
        loadChildren: () => import('./food-item/food-item.module').then(m => m.NooMeFoodItemModule),
      },
      {
        path: 'serving',
        loadChildren: () => import('./serving/serving.module').then(m => m.NooMeServingModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class NooMeEntityModule {}
