import React from 'react';
import { fireEvent, render, screen, waitFor } from '@testing-library/react';
import App from '../App';
import Home from '../components/Home';
import { BrowserRouter } from 'react-router-dom';
import AddDriver from '../components/AddDriver';
import ViewDriver from '../components/ViewDriver';
import Footer from '../components/Footer';

test('renders_App_with_Header_and_routing_links', () => {
  render(<App />);
  const headerTitleElement = screen.getByRole('heading', { name: /Car Taxi Management/i, level: 1 });
  expect(headerTitleElement).toBeInTheDocument();

  const driversLinkElement = screen.getByText(/View Drivers/i);
  expect(driversLinkElement).toBeInTheDocument();
});


test('renders_Home_component_with_Heading', () => {
  render(
    <BrowserRouter>
      <Home />
    </BrowserRouter>
  );
  const headingElement = screen.getByText(/Welcome to Car Taxi Management/i);
  expect(headingElement).toBeInTheDocument();
});

test('renders_Home_component_image_used_inside_div_home', () => {
  render(
    <BrowserRouter>
      <Home />
    </BrowserRouter>
  );
  const heading = screen.getByText(/Welcome to Car Taxi Management/i);
  const imageElement = screen.getByAltText(/background/i);
  expect(heading).toBeInTheDocument();
  expect(imageElement).toBeInTheDocument();
});

test('renders_AddDriver_component_form_from_Home', () => {
  render(<App />);
  const registerLink = screen.getByText(/Register Driver/i);
  fireEvent.click(registerLink);
  const heading = screen.getByText(/Register a New Driver/i);
  expect(heading).toBeInTheDocument();
});

test('renders_ViewDriver_title_correctly', () => {
  render(<App />);
  const viewDriversLink = screen.getByText(/View Drivers/i);
  fireEvent.click(viewDriversLink);
  const title = screen.getByText(/All Drivers/i);
  expect(title).toBeInTheDocument();
});

test('renders_form_input_fields_and_labels', () => {
  render(
    <BrowserRouter>
      <AddDriver />
    </BrowserRouter>
  );

  expect(screen.getByLabelText(/Driver Name:/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/City:/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/Phone:/i)).toBeInTheDocument();
  expect(screen.getByLabelText(/Vehicle Type:/i)).toBeInTheDocument(); // FIXED
  expect(screen.getByLabelText(/License Number:/i)).toBeInTheDocument(); // FIXED
  expect(screen.getByLabelText(/Assigned Area:/i)).toBeInTheDocument(); // FIXED
});

test('displays_validation_errors_with_empty_input', async () => {
  render(
    <BrowserRouter>
      <AddDriver />
    </BrowserRouter>
  );

  const submitButton = screen.getByText(/Register Driver/i);
  fireEvent.click(submitButton);

  await waitFor(() => {
    expect(screen.getByText(/Driver Name is required/i)).toBeInTheDocument();
    expect(screen.getByText(/City is required/i)).toBeInTheDocument();
    expect(screen.getByText(/Valid 10-digit phone number required/i)).toBeInTheDocument();
    expect(screen.getByText(/Vehicle Type is required/i)).toBeInTheDocument(); // FIXED
    expect(screen.getByText(/License Number is required/i)).toBeInTheDocument(); // FIXED
    expect(screen.getByText(/Assigned Area is required/i)).toBeInTheDocument(); // FIXED
  });
});

test('checks_submit_form_functionality', async () => {
  render(
    <BrowserRouter>
      <AddDriver />
    </BrowserRouter>
  );

  fireEvent.change(screen.getByLabelText(/Driver Name:/i), { target: { value: 'Test Driver' } });
  fireEvent.change(screen.getByLabelText(/City:/i), { target: { value: 'Chennai' } });
  fireEvent.change(screen.getByLabelText(/Phone:/i), { target: { value: '9876543210' } });
  fireEvent.change(screen.getByLabelText(/Vehicle Type:/i), { target: { value: 'SUV' } }); // FIXED
  fireEvent.change(screen.getByLabelText(/License Number:/i), { target: { value: 'DL123456' } }); // FIXED
  fireEvent.change(screen.getByLabelText(/Assigned Area:/i), { target: { value: 'Zone A' } }); // FIXED

  const fetchMock = jest.spyOn(global, 'fetch').mockResolvedValue({ ok: true });

  fireEvent.click(screen.getByText(/Register Driver/i));

  await waitFor(() => {
    expect(fetchMock).toHaveBeenCalledWith(expect.any(String), expect.objectContaining({
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({
        driverName: 'Test Driver',
        city: 'Chennai',
        phone: '9876543210',
        vehicleType: 'SUV',
        licenseNumber: 'DL123456',
        assignedArea: 'Zone A',
      })
    }));
  });

  fetchMock.mockRestore();
});

test('fetches_data_from_the_backend_when_the_component_mounts', async () => {
  const mockDriverData = [
    {
      driverId: 1,
      driverName: 'Arjun Kumar',
      city: 'Delhi',
      phone: '9123456789',
      vehicleType: 'Sedan',
      licenseNumber: 'DL09CZ8888',
      assignedArea: 'Zone C',
    }
  ];

  jest.spyOn(global, 'fetch').mockResolvedValueOnce({
    ok: true,
    json: async () => mockDriverData
  });

  render(
    <BrowserRouter>
      <ViewDriver />
    </BrowserRouter>
  );

  await waitFor(() => {
    expect(screen.getByText('Arjun Kumar')).toBeInTheDocument();
    expect(screen.getByText('Delhi')).toBeInTheDocument();
    expect(screen.getByText('9123456789')).toBeInTheDocument();
    expect(screen.getByText('Sedan')).toBeInTheDocument();
    expect(screen.getByText('DL09CZ8888')).toBeInTheDocument();
    expect(screen.getByText('Zone C')).toBeInTheDocument();
  });

  global.fetch.mockRestore();
});

test('renders_Car_Taxi_Management_System_in_the_footer', () => {
  render(<Footer />);
  const footerText = screen.getByText(/© 2025 Car Taxi Management System/i);
  expect(footerText).toBeInTheDocument();
});
