import { Money } from '../../../core/common/model/Money'
import { Currencies } from '@/core/accounting/model/accounts/Currencies'
import { Nullable } from '../../../core/common/base/lang/Nullable'

export class MoneyFormatter {
    static format(money: Money, currency?: Nullable<Currencies>): string {
        const amount = money.format(2, '.', ',')
        if (!currency) return amount
        return amount + ' ' + currency
    }
}
