import { Transaction } from './transaction.model';
export class TransactionsDto {
  currentPage?: number;
  totalItems?: number;
  totalPages?: number;
  transactions?: Transaction[];
  itemsPerPage?: number;
}
