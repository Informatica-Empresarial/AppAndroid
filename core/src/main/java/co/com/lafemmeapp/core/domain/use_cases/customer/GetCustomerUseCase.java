package co.com.lafemmeapp.core.domain.use_cases.customer;

import co.com.lafemmeapp.core.domain.entities.Customer;
import co.com.lafemmeapp.core.domain.mappers.APICustomerCustomerMapper;
import co.com.lafemmeapp.core.domain.use_cases.UseCase;
import co.com.lafemmeapp.dataprovider.repo.RepositoryFactory;
import co.com.lafemmeapp.dataprovider.repo.interfaces.IRepositoryFactory;
import io.reactivex.Observable;
import io.reactivex.Scheduler;

/**
 * Created by oscargallon on 4/8/17.
 */
public class GetCustomerUseCase extends UseCase<Customer, String> {

    private IRepositoryFactory mRepositoryFactory;
    public GetCustomerUseCase(Scheduler subscribeOnScheduler,
                              Scheduler observerOnScheduler) {
        super(subscribeOnScheduler, observerOnScheduler);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Observable<Customer> buildUseCaseObservable(String id) {
        return mRepositoryFactory.getCustomerRepository()
                .getCustomer(id)
                .map(APICustomerCustomerMapper.getInstance());
    }
}
