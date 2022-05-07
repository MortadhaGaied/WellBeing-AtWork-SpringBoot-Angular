import { Pipe, PipeTransform } from "@angular/core";

@Pipe({
  name: "userSearchFilter",
})
export class UserSearchFilterPipe implements PipeTransform {
  transform(users: any[], searchValue: string): any[] {
    if (!users || !searchValue) {
      return users;
    }
    return users.filter((user) =>
      user.displayName.toLowerCase().includes(searchValue.toLowerCase())
    );
  }
}
