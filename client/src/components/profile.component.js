import React, { Component } from "react";
import { Redirect } from 'react-router-dom';
import { connect } from "react-redux";
class Profile extends Component {
  render() {
    const { user: currentUser } = this.props;
    if (!currentUser) {
      return <Redirect to="/login" />;
    }
    return (
      <div className="container">
        <header className="jumbotron">
          <h3>
            <strong>{currentUser.email}</strong> Profile
          </h3>
        </header>
        <p>
          <strong>Token:</strong> {currentUser.accessToken.substring(0, 20)} ...{" "}
          {currentUser.accessToken.substr(currentUser.accessToken.length - 20)}
        </p>
        <p>
          <strong>Id:</strong> {currentUser.id}
        </p>
        <p>
          <strong>First name:</strong> {currentUser.firstName}
        </p>
		<p>
          <strong>Last name:</strong> {currentUser.lastName}
        </p>
        <strong>Authorities:</strong>
        <ul>
          {currentUser.roles &&
            currentUser.roles.map((role, index) => <li key={index}>{role}</li>)}
        </ul>
      </div>
    );
  }
}
function mapStateToProps(state) {
  const { user } = state.auth;
  return {
    user,
  };
}
export default connect(mapStateToProps)(Profile);